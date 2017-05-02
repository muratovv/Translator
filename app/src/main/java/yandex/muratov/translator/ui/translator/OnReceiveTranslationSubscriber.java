package yandex.muratov.translator.ui.translator;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.util.Objects;

import yandex.muratov.translator.R;
import yandex.muratov.translator.storage.data.HistoryRow;
import yandex.muratov.translator.translate.api.TranslatorModelSubscriber;
import yandex.muratov.translator.translate.data.DictionaryAnswer;
import yandex.muratov.translator.translate.data.TranslateAnswer;
import yandex.muratov.translator.ui.FormatterStrategies;
import yandex.muratov.translator.ui.HistoryStorageContext;

import static yandex.muratov.translator.translate.data.DataCodes.OK_RESPONSE_CODE;
import static yandex.muratov.translator.translate.data.DataCodes.getResourceByError;
import static yandex.muratov.translator.translate.data.DataCodes.isValid;

class OnReceiveTranslationSubscriber implements TranslatorModelSubscriber {

    private static String TAG = OnReceiveTranslationSubscriber.class.getSimpleName();

    private Context appContext;
    private HistoryStorageContext historyContext;
    private TranslatorInputView input;
    private TranslatorOutputView output;

    public OnReceiveTranslationSubscriber(Context appContext,
                                          HistoryStorageContext historyContext,
                                          TranslatorInputView inputView,
                                          TranslatorOutputView outputView) {
        this.appContext = appContext;
        this.input = inputView;
        this.output = outputView;
        this.historyContext = historyContext;
    }

    @Override
    public void onTranslateResponse(TranslateAnswer response) {
        if (isValid(response)) {
            putTranslateToUI(response);
            saveToHistory(response);
        } else {
            Integer resource = getResourceByError(response);
            if (!Objects.equals(resource, OK_RESPONSE_CODE)) {
                Toast.makeText(appContext, appContext.getResources().getText(resource), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void saveToHistory(TranslateAnswer response) {
        if (historyContext != null && historyContext.getConnector() != null) {
            String sourceText = getSourceText();
            if (sourceText.length() == 0) return;
            String translation = FormatterStrategies.wrapList.toText(response.getTexts());
            HistoryRow row = HistoryRow.newRow(sourceText, translation,
                    response.getLanguage());
            historyContext.getConnector().put(row);
        }
    }

    private String getSourceText() {
        return input.getSourceEditText().getText().toString();
    }

    private void putTranslateToUI(TranslateAnswer response) {
        String text = FormatterStrategies.wrapList.toText(response.getTexts());
        output.getTranslationTextView().setText(text);
    }

    @Override
    public void onTranslateRequestFail(Throwable error) {
        Toast.makeText(appContext, appContext.getResources().getText(R.string.translation_api_error_unknown_network_error), Toast.LENGTH_SHORT).show();
        Log.e(TAG, "onTranslateResponseFail:", error);
    }

    @Override
    public void onDictionaryResponse(DictionaryAnswer response) {
        if (isValid(response)) {
            putDictionaryToUI(response);
        } else {
            Integer resource = getResourceByError(response);
            if (!Objects.equals(resource, OK_RESPONSE_CODE)) {
                Toast.makeText(appContext, appContext.getResources().getText(resource), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void putDictionaryToUI(DictionaryAnswer response) {
        DictionaryAdapter adapter = ((DictionaryAdapter) output.getDictionaryListView().getAdapter());
        adapter.setAnswer(response.getDefinitions());
    }

    @Override
    public void onDictionaryRequestFail(Throwable error) {
        Toast.makeText(appContext, appContext.getResources().getText(R.string.translation_api_error_unknown_network_error), Toast.LENGTH_SHORT).show();
        Log.e(TAG, "onDictionaryResponseFail:", error);
    }
}
