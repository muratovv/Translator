package yandex.muratov.translator.translate;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import deferred_queue.core.Callback;
import deferred_queue.core.DeferredQueue;
import deferred_queue.core.Delay;
import yandex.muratov.translator.translate.api.Droppable;
import yandex.muratov.translator.translate.api.NetworkModelObserver;
import yandex.muratov.translator.translate.api.TranslationModel;
import yandex.muratov.translator.translate.api.TranslatorModelSubscriber;
import yandex.muratov.translator.translate.data.DataCodes;
import yandex.muratov.translator.translate.data.DictionaryAnswer;
import yandex.muratov.translator.translate.data.Language;
import yandex.muratov.translator.translate.data.TranslateAnswer;

/**
 * Class accessor for translation model.
 */
public class TranslationController implements NetworkModelObserver, Droppable {
    private static String TAG = TranslationController.class.getSimpleName();

    /**
     * Active translation model
     */
    private TranslationModel net;

    /**
     * Model subscriber, there is available only one subscriber
     */
    private TranslatorModelSubscriber uiSubscriber;

    /**
     * Source language of translation
     */
    private Language sourceLang;

    /**
     * Target language of translation
     */
    private Language targetLang;

    /**
     * Keeper of last {@link yandex.muratov.translator.translate.api.TranslateApi} response
     */
    private AnswerHolder<TranslateAnswer> translateHolder = new AnswerHolder<>();

    /**
     * Keeper of last {@link yandex.muratov.translator.translate.api.DictionaryApi} response
     */
    private AnswerHolder<DictionaryAnswer> dictionaryHolder = new AnswerHolder<>();

    private DeferredQueue<String> translationQueue;

    public TranslationController(TranslationModel net,
                                 Language defaultSourceLang, Language defaultTargetLang) {
        this.net = net;
        this.sourceLang = defaultSourceLang;
        this.targetLang = defaultTargetLang;
        this.net.subscribe(new TranslatorModelSubscriber() {
            @Override
            public void onTranslateResponse(TranslateAnswer response) {
                Log.d(TAG, String.format("onTranslateResponse: %s", response));
                translateHolder.setValue(response);
                if (uiSubscriber == null) return;

                if (response == null) {
                    uiSubscriber.onTranslateRequestFail(new IllegalArgumentException());
                } else {
                    uiSubscriber.onTranslateResponse(response);
                    translateHolder.getAndErase();
                }
            }

            @Override
            public void onDictionaryResponse(DictionaryAnswer response) {
                dictionaryHolder.setValue(response);
                if (uiSubscriber == null) return;

                if (response == null) {
                    uiSubscriber.onDictionaryRequestFail(new IllegalArgumentException());
                } else {
                    uiSubscriber.onDictionaryResponse(response);
                    dictionaryHolder.getAndErase();
                }
            }

            @Override
            public void onTranslateRequestFail(Throwable error) {
                if (uiSubscriber == null)
                    return;

                uiSubscriber.onTranslateRequestFail(error);
            }

            @Override
            public void onDictionaryRequestFail(Throwable error) {
                if (uiSubscriber == null)
                    return;

                uiSubscriber.onDictionaryRequestFail(error);
            }
        });
        translationQueue = initQueue();
    }

    private DeferredQueue<String> initQueue() {
        DeferredQueue<String> queue = new DeferredQueue<>();
        queue.setOnTimeExpiredCallback(new Callback<String>() {
            @Override
            public void call(String text) {
                dropLast();
                translateRequest(text);
            }
        });
        queue.setOnForceDequeCallback(new Callback<String>() {
            @Override
            public void call(String ignored) {
                dropLast();
            }
        });
        return queue;
    }

    @Override
    public void subscribe(TranslatorModelSubscriber subscriber) {
        this.uiSubscriber = subscriber;
        notifyLastResponse();
    }

    /**
     * Subscriber receive last response if it wat not processed
     */
    private void notifyLastResponse() {
        DictionaryAnswer dict = dictionaryHolder.getAndErase();
        if (dict != null) {
            Log.d(TAG, "notifyLastResponse: dict");
            uiSubscriber.onDictionaryResponse(dict);
        }

        TranslateAnswer translate = translateHolder.getAndErase();
        if (translate != null) {
            Log.d(TAG, "notifyLastResponse: translate");
            uiSubscriber.onTranslateResponse(translate);
        }
    }

    @Override
    public void unsubscribe() {
        this.uiSubscriber = null;
    }

    @Override
    public void dropLast() {
        net.dropLast();
    }

    @Override
    public void dropConnection() {
        net.dropConnection();
    }

    public Language getSourceLang() {
        return sourceLang;
    }

    public Language getTargetLang() {
        return targetLang;
    }

    public void translate(final String text) {
        Log.d(TAG, String.format("translate: %s", text));
        if (text == null || text.trim().equals("")) {
            translationQueue.forcePull();
            sendEmptyAnswer();
            dropLast();
            return;
        }
//        dropLast();
//        translateRequest(text);
        translationQueue.insert(text, Delay.delay(1, TimeUnit.SECONDS));
    }

    private void translateRequest(String text) {
        String rawLang = makeApiCodeAdapter(sourceLang, targetLang);
        net.translateRequest(rawLang, text);
        net.dictionaryRequest(rawLang, text);
    }

    private boolean sendEmptyAnswer() {
        if (uiSubscriber != null) {
            uiSubscriber.onTranslateResponse(new TranslateAnswer()
                    .setCode(DataCodes.VALID_ANSWER_CODE));
            uiSubscriber.onDictionaryResponse(new DictionaryAnswer()
                    .setCode(DataCodes.VALID_ANSWER_CODE));
            return true;
        }
        return false;
    }

    private static String makeApiCodeAdapter(Language sourceLanguage, Language targetLanguage) {
        return sourceLanguage.getCode() + "-" + targetLanguage.getCode();
    }

    public TranslationController setSourceLanguage(Language source) {
        this.sourceLang = source;
        return this;
    }

    public TranslationController setTargetLanguage(Language target) {
        this.targetLang = target;
        return this;
    }

    private static class AnswerHolder<T> {
        T value;

        public T getAndErase() {
            T value = this.value;
            this.value = null;
            return value;
        }

        public void setValue(T value) {
            this.value = value;
        }
    }
}
