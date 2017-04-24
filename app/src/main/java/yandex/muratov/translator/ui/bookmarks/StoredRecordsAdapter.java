package yandex.muratov.translator.ui.bookmarks;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import yandex.muratov.translator.R;
import yandex.muratov.translator.storage.HistoryRow;

import static yandex.muratov.translator.ui.translator.DictionaryAdapter.TAG;
import static yandex.muratov.translator.util.AndroidUtil.findViewById;

public class StoredRecordsAdapter extends ArrayAdapter<HistoryRow> {


    public StoredRecordsAdapter(Context context, List<HistoryRow> dataset) {
        super(context, R.layout.item_bookmark, dataset);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Log.d(TAG, "getView: ");
        // Get the data item for this position
        HistoryRow row = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_bookmark, parent, false);
        }

        setButtonFavoriteState((ImageButton) findViewById(convertView, R.id.button_in_favorites), row.inFavorites());
        setText((TextView) findViewById(convertView, R.id.text_source_word), row.getSourceText());
        setText((TextView) findViewById(convertView, R.id.text_dest_word), row.getTranslationText());
        setText((TextView) findViewById(convertView, R.id.text_used_languages), row.getRawLang().toUpperCase());
        return convertView;
    }

    public BookmarkItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bookmark, parent, false);
        return new BookmarkItemViewHolder(view, this);
    }

    public void onBindViewHolder(BookmarkItemViewHolder holder, int position) {
        HistoryRow row = null;

        setButtonFavoriteState(holder.getInFavorites(), row.inFavorites());
        setText(holder.getSourceText(), row.getSourceText());
        setText(holder.getTranslationText(), row.getTranslationText());
        setText(holder.getUsedLanguages(), row.getRawLang().toUpperCase());
    }

    public void insert(List<HistoryRow> newData) {
        super.clear();
        this.notifyDataSetInvalidated();
        super.addAll(newData);
        notifyDataSetChanged();
    }

    private static void setButtonFavoriteState(ImageButton buttonFavoriteState, boolean isFavorite) {
        if (isFavorite) {
            buttonFavoriteState.setImageResource(R.drawable.ic_bookmark_used);
        } else {
            buttonFavoriteState.setImageResource(R.drawable.ic_bookmark_unused);
        }
    }


    private static void setText(TextView view, String text) {
        view.setText(text);
    }
}
