package yandex.muratov.translator.ui.translator;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import yandex.muratov.translator.R;

public class TranslatorToolbar extends View {

    private Button pickSourceLang;
    private Button pickTargetLang;
    private ImageButton swapButton;

    public TranslatorToolbar(Context context) {
        super(context);
    }

    public TranslatorToolbar(View view) {
        super(view.getContext());
        initElements(view);
    }

    private void initElements(View view) {
        pickSourceLang = initPickSourceLang(view);
        pickTargetLang = initPickTargetLang(view);
        swapButton = initSwapButton(view);
    }

    private static Button initPickSourceLang(View view) {
        return (Button) view.findViewById(R.id.button_pick_source_lang);
    }

    private static ImageButton initSwapButton(View view) {
        return (ImageButton) view.findViewById(R.id.button_swap_lang);
    }

    private static Button initPickTargetLang(View view) {
        return (Button) view.findViewById(R.id.button_pick_target_lang);
    }

    public Button getPickSourceLang() {
        return pickSourceLang;
    }

    public Button getPickTargetLang() {
        return pickTargetLang;
    }

    public ImageButton getSwapButton() {
        return swapButton;
    }
}