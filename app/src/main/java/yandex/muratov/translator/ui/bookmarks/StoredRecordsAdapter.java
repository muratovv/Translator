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
import yandex.muratov.translator.storage.data.HistoryRow;
import yandex.muratov.translator.storage.StorageController;

import static yandex.muratov.translator.ui.translator.DictionaryAdapter.TAG;
import static yandex.muratov.translator.util.AndroidUtil.findViewById;

public class StoredRecordsAdapter extends ArrayAdapter<HistoryRow> {

    private StorageController connector;

    public StoredRecordsAdapter(Context context, List<HistoryRow> dataset, StorageController connector) {
        super(context, R.layout.item_bookmark, dataset);
        this.connector = connector;
        Log.d(TAG, String.format("StoredRecordsAdapter: my hash=%d", hashCode()));
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
        if (row != null) {
            setButtonFavoriteState((ImageButton) findViewById(convertView, R.id.button_in_favorites),
                    connector, row);
            setText((TextView) findViewById(convertView, R.id.text_source_word), row.getSourceText());
            setText((TextView) findViewById(convertView, R.id.text_dest_word), row.getTranslationText());
            setText((TextView) findViewById(convertView, R.id.text_used_languages), row.getRawLang().toUpperCase());
        }
        return convertView;
    }

    public void replace(List<HistoryRow> newData) {
        super.clear();
        this.notifyDataSetInvalidated();
        super.addAll(newData);
        notifyDataSetChanged();
    }

    private static void setButtonFavoriteState(ImageButton buttonFavoriteState,
                                               final StorageController connector,
                                               final HistoryRow row) {
        buttonFavoriteState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (connector != null) {
                    connector.setFavorite(row, !row.inFavorites());
                }
            }
        });
        if (row.inFavorites()) {
            buttonFavoriteState.setImageResource(R.drawable.ic_bookmark_used);
        } else {
            buttonFavoriteState.setImageResource(R.drawable.ic_bookmark_unused);
        }
    }

    private static void setText(TextView view, String text) {
        view.setText(text);
    }
}
