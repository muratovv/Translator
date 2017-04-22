package yandex.muratov.translator.ui.translator;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import yandex.muratov.translator.R;

public class LanguagePickerToolbar extends RelativeLayout {

    private ImageButton backButton;

    public LanguagePickerToolbar(Context context) {
        super(context);
        initElements(context);
    }

    public LanguagePickerToolbar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initElements(context);
    }

    public LanguagePickerToolbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initElements(context);
    }

    public LanguagePickerToolbar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initElements(context);
    }

    private void initElements(Context appContext) {
        View view = inflate(appContext, R.layout.toolbar_language_picker, this);
        backButton = initBackButton(view);
    }

    private ImageButton initBackButton(View view) {
        return (ImageButton) view.findViewById(R.id.button_back_language_picker);
    }

    public ImageButton getBackButton() {
        return backButton;
    }
}
