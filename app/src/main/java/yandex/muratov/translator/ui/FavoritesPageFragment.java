package yandex.muratov.translator.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import yandex.muratov.translator.R;

public class FavoritesPageFragment extends BasePageFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        searchBar.getQueryLine().setHint(getActivity().getResources().getString(R.string.hint_find_in_favorites));
        return view;
    }

    public static FavoritesPageFragment getInstance() {
        return new FavoritesPageFragment();
    }
}
