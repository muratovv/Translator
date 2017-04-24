package yandex.muratov.translator.ui.bookmarks;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import yandex.muratov.translator.R;
import yandex.muratov.translator.storage.HistoryRow;
import yandex.muratov.translator.storage.api.StorageOperations;

public class FavoritesPageFragment extends BasePageFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = super.onCreateView(inflater, container, savedInstanceState);
        return view;
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
                return value.inFavorites();
            }
        };
    }


    public static FavoritesPageFragment getInstance() {
        return new FavoritesPageFragment();
    }
}
