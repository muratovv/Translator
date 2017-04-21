package yandex.muratov.translator.network;

import yandex.muratov.translator.network.api.Droppable;
import yandex.muratov.translator.network.api.ModelObserver;
import yandex.muratov.translator.network.api.NetworkTranslatorModel;
import yandex.muratov.translator.network.api.TranslatorModelSubscriber;
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
            public void onDictionaryResponse(DictionaryAnswer response) {
                if (uiSubscriber == null) return;

                if (response == null) {
                    uiSubscriber.onDictionaryRequestFail(new IllegalArgumentException());
                } else uiSubscriber.onDictionaryResponse(response);
            }

            @Override
            public void onDictionaryRequestFail(Throwable error) {
                if (uiSubscriber == null) return;

                uiSubscriber.onDictionaryRequestFail(error);
            }

            @Override
            public void onTranslateResponse(TranslateAnswer response) {
                if (uiSubscriber == null) return;

                if (response == null) {
                    uiSubscriber.onDictionaryRequestFail(new IllegalArgumentException());
                } else uiSubscriber.onTranslateResponse(response);
            }

            @Override
            public void onTranslateRequestFail(Throwable error) {
                if (uiSubscriber == null) return;

                uiSubscriber.onTranslateRequestFail(error);
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
        if (uiSubscriber == null) return;
        net.dropLastRequest();
        String apiLang = makeApiCodeAdapter(sourceLanguage, targetLanguage);
        net.translateRequest(apiLang, text);
        // TODO: 22.04.17 optimize network with checking number of words
        net.dictionaryRequest(apiLang, text);
    }

    private static String makeApiCodeAdapter(Language sourceLanguage, Language targetLanguage) {
        return sourceLanguage.getCode() + "-" + targetLanguage.getCode();
    }
}
