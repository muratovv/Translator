package yandex.muratov.translator.ui.bookmarks;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import yandex.muratov.translator.storage.HistoryRow;
import yandex.muratov.translator.storage.api.StorageOperations;

public class BookmarkPageFragment extends BasePageFragment {
    private static String TAG = BookmarkPageFragment.class.getSimpleName();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected StorageOperations.Predicate<HistoryRow> getSearchPredicate(final String s) {
        return new StorageOperations.Predicate<HistoryRow>() {
            @Override
            public boolean apply(HistoryRow value) {
                return true;
            }
        };
    }

    public static BookmarkPageFragment getInstance() {
        Log.d(TAG, "getInstance: ");
        return new BookmarkPageFragment();
    }
}
