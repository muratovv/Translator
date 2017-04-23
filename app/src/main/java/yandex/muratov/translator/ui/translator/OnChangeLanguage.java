package yandex.muratov.translator.ui.translator;

import java.io.Serializable;

import yandex.muratov.translator.network.data.Language;

public interface OnChangeLanguage extends Serializable {
    String TAG = OnChangeLanguage.class.getSimpleName();

    void notify(Language language);
}
