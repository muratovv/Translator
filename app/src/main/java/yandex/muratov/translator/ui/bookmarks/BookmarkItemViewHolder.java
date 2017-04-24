package yandex.muratov.translator.ui.bookmarks;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import yandex.muratov.translator.R;

import static yandex.muratov.translator.util.AndroidUtil.findViewById;


class BookmarkItemViewHolder extends RecyclerView.ViewHolder {

    private ImageButton inFavorites;
    private TextView sourceText;
    private TextView translationText;
    private TextView usedLanguages;


    public BookmarkItemViewHolder(View itemView, StoredRecordsAdapter storedRecordsAdapter) {
        super(itemView);
        inFavorites = findViewById(itemView, R.id.button_in_favorites);
        sourceText = findViewById(itemView, R.id.text_source_word);
        translationText = findViewById(itemView, R.id.text_dest_word);
        usedLanguages = findViewById(itemView, R.id.text_used_languages);
    }

    public ImageButton getInFavorites() {
        return inFavorites;
    }

    public TextView getSourceText() {
        return sourceText;
    }

    public TextView getTranslationText() {
        return translationText;
    }

    public TextView getUsedLanguages() {
        return usedLanguages;
    }
}
