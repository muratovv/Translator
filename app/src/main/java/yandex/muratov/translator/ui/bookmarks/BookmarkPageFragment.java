package yandex.muratov.translator.ui.bookmarks;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import yandex.muratov.translator.storage.data.HistoryRow;
import yandex.muratov.translator.storage.api.StorageOperations;

public class BookmarkPageFragment extends BasePageFragment {
    private static String TAG = BookmarkPageFragment.class.getSimpleName();


    @Override
    protected StorageOperations.Predicate<HistoryRow> getSearchPredicate(final String s) {
        return new StorageOperations.Predicate<HistoryRow>() {
            @Override
            public boolean apply(HistoryRow value) {
                String query = s.toLowerCase();
                String sourceText = value.getSourceText().toLowerCase();
                String translateText = value.getTranslationText().toLowerCase();

                return sourceText.contains(query) || translateText.contains(query);
            }
        };
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activate();
    }

    public static BookmarkPageFragment getInstance() {
        Log.d(TAG, "getInstance: BookmarkPageFragment");
        return new BookmarkPageFragment();
    }
}
