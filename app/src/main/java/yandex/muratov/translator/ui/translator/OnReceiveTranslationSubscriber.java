package yandex.muratov.translator.ui.translator;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.util.Objects;

import yandex.muratov.translator.R;
import yandex.muratov.translator.network.api.TranslatorModelSubscriber;
import yandex.muratov.translator.network.data.DictionaryAnswer;
import yandex.muratov.translator.network.data.TranslateAnswer;
import yandex.muratov.translator.util.StringUtil;

import static yandex.muratov.translator.network.data.DataCodes.OK_CODE;
import static yandex.muratov.translator.network.data.DataCodes.getResourceByError;
import static yandex.muratov.translator.network.data.DataCodes.isValid;

class OnReceiveTranslationSubscriber implements TranslatorModelSubscriber {

    private static String TAG = OnReceiveTranslationSubscriber.class.getSimpleName();

    private Context context;
    private TranslatorOutputView output;

    public OnReceiveTranslationSubscriber(Context context, TranslatorOutputView outputView) {
        this.context = context;
        this.output = outputView;
    }

    @Override
    public void onTranslateResponse(TranslateAnswer response) {
        if (isValid(response)) {
            output.getTranslationTextEdit().setText(StringUtil.join(", ", response.getTexts()));
        } else {
            Integer resource = getResourceByError(response);
            if (!Objects.equals(resource, OK_CODE)) {
                Toast.makeText(context, context.getResources().getText(resource), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onTranslateRequestFail(Throwable error) {
        Toast.makeText(context, context.getResources().getText(R.string.translation_api_error_unknown_network_error), Toast.LENGTH_SHORT).show();
        Log.e(TAG, "onTranslateRequestFail:", error);
    }

    @Override
    public void onDictionaryResponse(DictionaryAnswer response) {

    }

    @Override
    public void onDictionaryRequestFail(Throwable error) {

    }
}
