package yandex.muratov.translator.ui;


import yandex.muratov.translator.translate.TranslationController;

public class TranslationContext {
    private TranslationController connector;

    public TranslationContext(TranslationController connector) {

        this.connector = connector;
    }

    public TranslationController getConnector() {
        return connector;
    }
}
