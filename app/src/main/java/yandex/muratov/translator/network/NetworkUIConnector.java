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

    public NetworkUIConnector(NetworkTranslatorModel net) {
        this.net = net;
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

    public void translate(Language sourceLanguage, Language targetLanguage, String text) {
        net.dropLastRequest();
        if (uiSubscriber == null || sendEmptyAnswer(text)) return;
        String apiLang = makeApiCodeAdapter(sourceLanguage, targetLanguage);
        translateRequest(text, apiLang);
    }

    private void translateRequest(String text, String apiLang) {
        net.translateRequest(apiLang, text);
        net.dictionaryRequest(apiLang, text);
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
}
