package yandex.muratov.translator.ui.translator;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import yandex.muratov.translator.R;

public class TranslatorOutputView extends RelativeLayout {

    private TextView translationText;
    private SwitchImageButton saveInBookmarksButton;
    private ListView dictionaryListView;


    public TranslatorOutputView(Context context) {
        super(context);
        initElements(context);
    }

    public TranslatorOutputView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initElements(context);
    }

    public TranslatorOutputView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initElements(context);
    }

    public TranslatorOutputView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initElements(context);
    }

    private void initElements(Context context) {
        View view = inflate(context, R.layout.partial_translator_output, this);
        translationText = initTranslationText(view);
        saveInBookmarksButton = initSaveInBookmarksButton(view);
        dictionaryListView = initAdditionalTranslationVariantsView(view);
    }

    private ListView initAdditionalTranslationVariantsView(View view) {
        ListView listView = (ListView) view.findViewById(R.id.dictionary);
        listView.setSelector(android.R.color.transparent);
        return listView;
    }

    private static TextView initTranslationText(View view) {
        return ((TextView) view.findViewById(R.id.text_translation));
    }

    private static SwitchImageButton initSaveInBookmarksButton(View view) {
        return ((SwitchImageButton) view.findViewById(R.id.button_save_in_bookmarks));
    }

    public TextView getTranslationTextView() {
        return translationText;
    }

    public ListView getDictionaryListView() {
        return dictionaryListView;
    }

    public SwitchImageButton getSaveInBookmarksButton() {
        return saveInBookmarksButton;
    }
}
