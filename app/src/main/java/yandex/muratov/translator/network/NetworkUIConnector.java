package yandex.muratov.translator.network;

import yandex.muratov.translator.network.api.Droppable;
import yandex.muratov.translator.network.api.ModelObserver;
import yandex.muratov.translator.network.api.NetworkTranslatorModel;
import yandex.muratov.translator.network.api.TranslatorModelSubscriber;
import yandex.muratov.translator.network.data.DataCodes;
import yandex.muratov.translator.network.data.DictionaryAnswer;
import yandex.muratov.translator.network.data.Language;
import yandex.muratov.translator.network.data.TranslateAnswer;


public class NetworkUIConnector implements ModelObserver, Droppable {
    private NetworkTranslatorModel net;
    private TranslatorModelSubscriber uiSubscriber;

    private Language sourceLang;
    private Language targetLang;

    public NetworkUIConnector(NetworkTranslatorModel net,
                              Language defaultSourceLang, Language defaultTargetLang) {
        this.net = net;
        this.sourceLang = defaultSourceLang;
        this.targetLang = defaultTargetLang;
        this.net.subscribe(new TranslatorModelSubscriber() {
            @Override
            public void onTranslateResponse(TranslateAnswer response) {
                if (uiSubscriber == null) return;

                if (response == null) {
                    uiSubscriber.onTranslateRequestFail(new IllegalArgumentException());
                } else uiSubscriber.onTranslateResponse(response);
            }

            @Override
            public void onDictionaryResponse(DictionaryAnswer response) {
                if (uiSubscriber == null) return;

                if (response == null) {
                    uiSubscriber.onDictionaryRequestFail(new IllegalArgumentException());
                } else uiSubscriber.onDictionaryResponse(response);
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
        net.dropLastRequest();
        if (uiSubscriber == null || sendEmptyAnswer(text)) return;
        translateRequest(text);
    }

    private void translateRequest(String text) {
        String rawLang = makeApiCodeAdapter(sourceLang, targetLang);
        net.translateRequest(rawLang, text);
        net.dictionaryRequest(rawLang, text);
    }

    private boolean sendEmptyAnswer(String text) {
        if (text.trim().length() == 0) {
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
}
