package yandex.muratov.translator.ui.translator;

import android.content.Context;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import yandex.muratov.translator.R;

public class TranslatorOutputView extends View {

    private TextView translationText;
    private ImageButton playSoundButton;
    private ImageButton saveInBookmarksButton;
    private ImageButton shareButton;
    private ImageButton fullscreenButton;
    private AdditionalTranslationVariantsView additionalTranslationVariantsView;


    public TranslatorOutputView(Context context) {
        super(context);
    }

    public TranslatorOutputView(View view) {
        super(view.getContext());
        initElements(view);
    }

    private void initElements(View view) {
        translationText = initTranslationText(view);
        playSoundButton = initPlaySoundButton(view);
        saveInBookmarksButton = initSaveInBookmarksButton(view);
        shareButton = initShareButton(view);
        fullscreenButton = initFullscreenButton(view);
        additionalTranslationVariantsView = initAdditionalTranslationVariantsView(view);
    }

    private AdditionalTranslationVariantsView initAdditionalTranslationVariantsView(View view) {
        View scrollVew = view.findViewById(R.id.scroll_additional_variants);
        return new AdditionalTranslationVariantsView(scrollVew);
    }

    private static TextView initTranslationText(View view) {
        return ((TextView) view.findViewById(R.id.text_translation));
    }

    private static ImageButton initPlaySoundButton(View view) {
        return ((ImageButton) view.findViewById(R.id.button_play_output_text));
    }

    private static ImageButton initSaveInBookmarksButton(View view) {
        return ((ImageButton) view.findViewById(R.id.button_save_in_bookmarks));
    }

    private static ImageButton initShareButton(View view) {
        return ((ImageButton) view.findViewById(R.id.button_share));
    }

    private static ImageButton initFullscreenButton(View view) {
        return ((ImageButton) view.findViewById(R.id.button_fullscreen));
    }

}
