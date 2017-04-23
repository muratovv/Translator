package yandex.muratov.translator.network;

import android.util.Log;

import yandex.muratov.translator.network.api.Droppable;
import yandex.muratov.translator.network.api.NetworkModelObserver;
import yandex.muratov.translator.network.api.NetworkTranslatorModel;
import yandex.muratov.translator.network.api.TranslatorModelSubscriber;
import yandex.muratov.translator.network.data.DataCodes;
import yandex.muratov.translator.network.data.DictionaryAnswer;
import yandex.muratov.translator.network.data.Language;
import yandex.muratov.translator.network.data.TranslateAnswer;


public class NetworkUIConnector implements NetworkModelObserver, Droppable {
    private static String TAG = NetworkUIConnector.class.getSimpleName();

    private NetworkTranslatorModel net;
    private TranslatorModelSubscriber uiSubscriber;

    private Language sourceLang;
    private Language targetLang;
    private AnswerHolder<TranslateAnswer> translateHolder = new AnswerHolder<>();
    private AnswerHolder<DictionaryAnswer> dictionaryHolder = new AnswerHolder<>();

    public NetworkUIConnector(NetworkTranslatorModel net,
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
    }

    @Override
    public void subscribe(TranslatorModelSubscriber subscriber) {
        this.uiSubscriber = subscriber;
        notifyLastResponse();
    }

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
    public void unSubscribe() {
        this.uiSubscriber = null;
    }

    @Override
    public void dropLastRequest() {
        net.dropLastRequest();
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

    public void translate(String text) {
        Log.d(TAG, String.format("translate: %s", text));
        if (text == null || text.trim().equals("")) {
            sendEmptyAnswer();
            dropLastRequest();
            return;
        }
        dropLastRequest();
        translateRequest(text);
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

    public NetworkUIConnector setSourceLanguage(Language source) {
        this.sourceLang = source;
        return this;
    }

    public NetworkUIConnector setTargetLanguage(Language target) {
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
