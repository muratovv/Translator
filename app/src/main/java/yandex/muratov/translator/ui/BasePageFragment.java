package yandex.muratov.translator.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import yandex.muratov.translator.R;

public abstract class BasePageFragment extends Fragment {

    protected ImageView eraseToolbarButton;
    protected OneLineSearchBar searchBar;
    protected RecyclerView listOfElements;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View baseView = inflater.inflate(R.layout.page_bookmark_screen, container, false);
        eraseToolbarButton = initEraseButton(baseView);
        searchBar = initSearchBar(baseView);
        listOfElements = initListOfElements(baseView);
        return baseView;
    }

    private static ImageView initEraseButton(View rootView) {
        return null;
    }

    private OneLineSearchBar initSearchBar(View rootView) {
        return new OneLineSearchBar(rootView.findViewById(R.id.bar_search));
    }

    private static RecyclerView initListOfElements(View rootView) {
        return ((RecyclerView) rootView.findViewById(R.id.list_of_translations));
    }

}
