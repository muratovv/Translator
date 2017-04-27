package yandex.muratov.translator.ui;


import yandex.muratov.translator.translate.NetworkUIConnector;

public class TranslationContext {
    private NetworkUIConnector connector;

    public TranslationContext(NetworkUIConnector connector) {

        this.connector = connector;
    }

    public NetworkUIConnector getConnector() {
        return connector;
    }
}
