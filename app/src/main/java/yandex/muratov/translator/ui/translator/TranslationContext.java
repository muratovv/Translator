package yandex.muratov.translator.ui.translator;


import yandex.muratov.translator.network.NetworkUIConnector;

public class TranslationContext {
    private NetworkUIConnector connector;

    public TranslationContext(NetworkUIConnector connector) {

        this.connector = connector;
    }

    public NetworkUIConnector getConnector() {
        return connector;
    }
}
