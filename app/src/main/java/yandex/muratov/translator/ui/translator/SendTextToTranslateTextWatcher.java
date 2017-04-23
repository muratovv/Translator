package yandex.muratov.translator.ui.translator;

import android.text.Editable;

import yandex.muratov.translator.ui.TranslationContext;
import yandex.muratov.translator.util.DefaultTextWatcher;

public class SendTextToTranslateTextWatcher extends DefaultTextWatcher {
    private TranslationContext context;

    public SendTextToTranslateTextWatcher(TranslationContext context) {
        this.context = context;
    }

    @Override
    public void afterTextChanged(Editable s) {
        String text = s.toString();
        if (context != null && context.getConnector() != null) {
            context.getConnector().translate(text);
        }
    }
}
