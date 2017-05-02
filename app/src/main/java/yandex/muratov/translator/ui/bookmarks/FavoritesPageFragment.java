package yandex.muratov.translator.ui.bookmarks;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import yandex.muratov.translator.R;
import yandex.muratov.translator.storage.data.HistoryRow;
import yandex.muratov.translator.storage.api.StorageOperations;

public class FavoritesPageFragment extends BasePageFragment {
    private static String TAG = FavoritesPageFragment.class.getSimpleName();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View baseView, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(baseView, savedInstanceState);
        searchBar.getQueryLine().setHint(getActivity().getResources().getString(R.string.hint_find_in_favorites));
    }

    @Override
    protected StorageOperations.Predicate<HistoryRow> getSearchPredicate(final String s) {
        return new StorageOperations.Predicate<HistoryRow>() {
            @Override
            public boolean apply(HistoryRow value) {
                String query = s.toLowerCase();
                String sourceText = value.getSourceText().toLowerCase();
                String translateText = value.getTranslationText().toLowerCase();

                return value.inFavorites() && (sourceText.contains(query) || translateText.contains(query));

            }
        };
    }

    public static FavoritesPageFragment getInstance() {
        Log.d(TAG, "getInstance: FavoritesPageFragment");
        return new FavoritesPageFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: Favorites");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: Favorites");
    }
}
