package yandex.muratov.translator.ui.translator;


import java.io.Serializable;

import yandex.muratov.translator.network.NetworkUIConnector;
import yandex.muratov.translator.network.data.Language;

public class TranslationContext implements Serializable {
    public static final String BUNDLE_TRANSLATION_CONTEXT = "bundle_translation_context";

    private Language source;
    private Language target;
    private Language ui;
    private NetworkUIConnector connector;

    public TranslationContext setSource(Language source) {
        this.source = source;
        return this;
    }

    public TranslationContext setTarget(Language target) {
        this.target = target;
        return this;
    }

    public TranslationContext setUi(Language ui) {
        this.ui = ui;
        return this;
    }

    public TranslationContext setConnector(NetworkUIConnector connector) {
        this.connector = connector;
        return this;
    }

    public Language getSource() {
        return source;
    }

    public Language getTarget() {
        return target;
    }

    public Language getUi() {
        return ui;
    }

    public NetworkUIConnector getConnector() {
        return connector;
    }
}
