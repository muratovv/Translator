package yandex.muratov.translator.ui.translator;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import yandex.muratov.translator.LanguagePickerActivity;
import yandex.muratov.translator.R;
import yandex.muratov.translator.network.NetworkUIConnector;
import yandex.muratov.translator.ui.ContextHolderFragment;

import static yandex.muratov.translator.ui.ContextHolderFragment.findContextFragment;

public class TranslatorScreenFragment extends Fragment {
    private static String TAG = TranslatorScreenFragment.class.getSimpleName();

    private TranslatorInputView input;
    private TranslatorToolbar toolbar;
    private TranslatorOutputView output;

    private ContextHolderFragment contextHolderFragment;

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
        initInternalState();
        initInternalViews(view, savedInstanceState);

        NetworkUIConnector connector = contextHolderFragment.getTranslationContext()
                .getConnector();
        connector.subscribe(new OnReceiveTranslationSubscriber(getContext(), output));
    }

    @Override
    public void onStop() {
        super.onStop();
        contextHolderFragment.getTranslationContext().getConnector().unSubscribe();
    }

    private void initInternalState() {
        contextHolderFragment = findContextFragment(this);
        textWatcher = new SendTextToTranslateTextWatcher(contextHolderFragment.getTranslationContext());
    }

    private void getInputArguments(Bundle state) {
        if (state != null) {
            Log.d(TAG, "getInputArguments: load from bundle");
        }
    }

    private void initInternalViews(View screenView, Bundle state) {
        toolbar = initToolbar(this, screenView);
        input = initInputView(screenView, textWatcher);
        output = initOutputView(screenView);
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();

    }

    private static TranslatorInputView initInputView(View rootView, TextWatcher textWatcher) {
        TranslatorInputView input = (TranslatorInputView) rootView.findViewById(R.id.input);
        input.getSourceEditText().addTextChangedListener(textWatcher);
        return input;
    }

    private static TranslatorToolbar initToolbar(final Fragment appliedFragment, View rootView) {
        TranslatorToolbar toolbar = (TranslatorToolbar) rootView.findViewById(R.id.toolbar_translator);
        toolbar.getPickSourceLang().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent languageSourcePickerIntent =
                        new Intent(appliedFragment.getContext(), LanguagePickerActivity.class);
                appliedFragment.startActivity(languageSourcePickerIntent);
            }
        });
        return toolbar;
    }

    private static TranslatorOutputView initOutputView(View rootView) {
        TranslatorOutputView translatorOutputView = (TranslatorOutputView) rootView.findViewById(R.id.output);
        return translatorOutputView;
    }

}
