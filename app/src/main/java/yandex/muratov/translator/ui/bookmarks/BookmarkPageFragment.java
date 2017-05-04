package yandex.muratov.translator.ui.bookmarks;

import android.util.Log;

import yandex.muratov.translator.storage.api.StorageOperations;
import yandex.muratov.translator.storage.data.HistoryRow;

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

    public static BookmarkPageFragment getInstance() {
        Log.d(TAG, "getInstance: BookmarkPageFragment");
        return new BookmarkPageFragment();
    }
}
