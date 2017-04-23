package yandex.muratov.translator.ui.translator;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import yandex.muratov.translator.R;

public class TranslatorToolbarView extends RelativeLayout {

    private Button pickSourceLang;
    private Button pickTargetLang;
    private ImageButton swapButton;

    public TranslatorToolbarView(Context context) {
        super(context);
        initElements(context);
    }

    public TranslatorToolbarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initElements(context);
    }

    public TranslatorToolbarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initElements(context);
    }

    public TranslatorToolbarView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initElements(context);
    }

    private void initElements(Context context) {
        View view = inflate(context, R.layout.toolbar_translator, this);
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
