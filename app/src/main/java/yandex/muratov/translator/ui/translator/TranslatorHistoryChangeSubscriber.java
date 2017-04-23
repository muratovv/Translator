package yandex.muratov.translator.ui.translator;

import android.util.Log;

import yandex.muratov.translator.R;
import yandex.muratov.translator.storage.HistoryRow;
import yandex.muratov.translator.storage.api.OnChangeStorage;
import yandex.muratov.translator.storage.api.Result;

import static yandex.muratov.translator.ui.translator.SwitchImageButton.State.OFF;
import static yandex.muratov.translator.ui.translator.SwitchImageButton.State.ON;

class TranslatorHistoryChangeSubscriber implements OnChangeStorage {
    private static String TAG = TranslatorHistoryChangeSubscriber.class.getSimpleName();

    private TranslatorOutputView output;
    private HistoryRow last;

    public TranslatorHistoryChangeSubscriber() {

    }

    public void setOutputView(TranslatorOutputView output) {
        this.output = output;
    }

    @Override
    public void onInsertCallback(HistoryRow actual) {
        last = actual;
        Log.d(TAG, String.format("onInsertCallback: %s with %s", last.getSourceText(), last.isFavorites()));
        if (output == null) return;
        if (last.isFavorites()) {
            switchShareButtonState(ON);
        } else {
            switchShareButtonState(OFF);
        }
    }

    private void switchShareButtonState(SwitchImageButton.State state) {
        switch (state) {
            case OFF:
                output.getSaveInBookmarksButton().setImageResource(R.drawable.ic_bookmark_unused);
                break;
            case ON:
                output.getSaveInBookmarksButton().setImageResource(R.drawable.ic_bookmark_used);
                break;
        }
    }


    @Override
    public void onRemoveByPredicate(Result<HistoryRow> removed) {

    }

    @Override
    public void onGetByPredicate(Result<HistoryRow> result) {

    }

    public HistoryRow getLast() {
        return last;
    }
}
