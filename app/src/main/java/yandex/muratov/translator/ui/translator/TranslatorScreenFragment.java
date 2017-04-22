package yandex.muratov.translator.ui.translator;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import yandex.muratov.translator.R;

import static yandex.muratov.translator.ui.translator.TranslationContext.*;

public class TranslatorScreenFragment extends Fragment {
    private static String TAG = TranslatorScreenFragment.class.getSimpleName();

    private TranslatorInputView input;
    private TranslatorToolbar toolbar;
    private TranslatorOutputView output;
    private TranslationContext translationContext;
    private SendTextToTranslateTextWatcher textWatcher;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_translator_screen, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getInputArguments(getArguments());
        initInternalViews(view, savedInstanceState);
    }

    private void getInputArguments(Bundle state) {
        if (state != null) {
            Log.d(TAG, "getInputArguments: load from bundle");
            translationContext = ((TranslationContext) state.get(BUNDLE_TRANSLATION_CONTEXT));
            textWatcher = new SendTextToTranslateTextWatcher(translationContext);
        }
    }

    private void initInternalViews(View screenView, Bundle state) {
        toolbar = initToolbar(screenView);
        input = initInputView(screenView, textWatcher);
        output = initOutputView(screenView);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (translationContext.getConnector() != null) {
            translationContext.getConnector()
                    .subscribe(new OnReceiveTranslationSubscriber(getContext(), output));
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (translationContext.getConnector() != null) {
            translationContext.getConnector().unSubscribe();
        }
    }

    private static TranslatorInputView initInputView(View rootView, TextWatcher textWatcher) {
        TranslatorInputView input = (TranslatorInputView) rootView.findViewById(R.id.input);
        input.getSourceEditText().addTextChangedListener(textWatcher);
        return input;
    }

    private static TranslatorToolbar initToolbar(View rootView) {
        return ((TranslatorToolbar) rootView.findViewById(R.id.toolbar_translator));
    }

    private static TranslatorOutputView initOutputView(View rootView) {
        return ((TranslatorOutputView) rootView.findViewById(R.id.output));
    }

}
