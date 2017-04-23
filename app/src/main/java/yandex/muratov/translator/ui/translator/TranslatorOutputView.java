package yandex.muratov.translator.ui.translator;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import yandex.muratov.translator.R;

public class TranslatorOutputView extends RelativeLayout {

    private TextView translationText;
    private ImageButton playSoundButton;
    private SwitchImageButton saveInBookmarksButton;
    private ImageButton shareButton;
    private ImageButton fullscreenButton;
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
        playSoundButton = initPlaySoundButton(view);
        saveInBookmarksButton = initSaveInBookmarksButton(view);
        shareButton = initShareButton(view);
        fullscreenButton = initFullscreenButton(view);
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

    private static ImageButton initPlaySoundButton(View view) {
        return ((ImageButton) view.findViewById(R.id.button_play_output_text));
    }

    private static SwitchImageButton initSaveInBookmarksButton(View view) {
        return ((SwitchImageButton) view.findViewById(R.id.button_save_in_bookmarks));
    }

    private static ImageButton initShareButton(View view) {
        return ((ImageButton) view.findViewById(R.id.button_share));
    }

    private static ImageButton initFullscreenButton(View view) {
        return ((ImageButton) view.findViewById(R.id.button_fullscreen));
    }

    public TextView getTranslationTextView() {
        return translationText;
    }

    public ListView getDictionaryListView() {
        return dictionaryListView;
    }

    public SwitchImageButton getSaveInBookmarksButton(){
        return saveInBookmarksButton;
    }
}
