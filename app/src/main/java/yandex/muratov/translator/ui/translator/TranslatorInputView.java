package yandex.muratov.translator.ui.translator;


import android.content.Context;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import yandex.muratov.translator.R;
import yandex.muratov.translator.util.KeyboardUtil;

public class TranslatorInputView extends RelativeLayout {

    private ImageButton speechKeyboardButton;
    private ImageButton playTextButton;
    private ImageButton eraseButton;
    private EditText sourceEditText;

    public TranslatorInputView(Context context) {
        super(context);
        initElements(context);
    }

    public TranslatorInputView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initElements(context);
    }

    public TranslatorInputView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initElements(context);
    }

    public TranslatorInputView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initElements(context);
    }

    private void initElements(Context context) {
        View view = inflate(context, R.layout.partial_translator_input, this);
        speechKeyboardButton = initSpeechKeyboardButton(view);
        playTextButton = initPlayTextButton(view);
        eraseButton = initEraseButton(view);
        sourceEditText = initSourceEditText(view);

        getSourceEditText().setRawInputType(InputType.TYPE_CLASS_TEXT);

        getEraseButton().setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                eraseText();
            }
        });
    }

    private void eraseText() {
        getSourceEditText().setText("");
        KeyboardUtil.showKeyboard(getContext(), getSourceEditText());
    }

    private static ImageButton initSpeechKeyboardButton(View view) {
        return ((ImageButton) view.findViewById(R.id.button_speech_keyboard));
    }

    private static ImageButton initPlayTextButton(View view) {
        return ((ImageButton) view.findViewById(R.id.button_play));
    }

    private static ImageButton initEraseButton(View view) {
        return ((ImageButton) view.findViewById(R.id.button_erase));
    }

    private static EditText initSourceEditText(View view) {
        return ((EditText) view.findViewById(R.id.edit_input));
    }

    public ImageButton getSpeechKeyboardButton() {
        return speechKeyboardButton;
    }

    public ImageButton getPlayTextButton() {
        return playTextButton;
    }

    public ImageButton getEraseButton() {
        return eraseButton;
    }

    public EditText getSourceEditText() {
        return sourceEditText;
    }
}
