package yandex.muratov.translator.ui.translator;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import yandex.muratov.translator.R;

public class TranslatorScreenFragment extends Fragment {

    private TranslatorInputView input;
    private TranslatorToolbar toolbar;
    private TranslatorOutputView output;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View screenView = inflater.inflate(R.layout.fragment_translator_screen, container, false);
        input = initInputView(screenView);
        toolbar = initToolbar(screenView);
        output = initOutputView(screenView);
        return screenView;
    }

    private static TranslatorInputView initInputView(View rootView) {
        return new TranslatorInputView(rootView.findViewById(R.id.input));
    }

    private static TranslatorToolbar initToolbar(View rootView) {
        return new TranslatorToolbar(rootView.findViewById(R.id.toolbar_translator));
    }

    private static TranslatorOutputView initOutputView(View rootView) {
        return new TranslatorOutputView(rootView.findViewById(R.id.output));
    }

}
