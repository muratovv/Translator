package yandex.muratov.translator.ui.bookmarks;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import yandex.muratov.translator.R;
import yandex.muratov.translator.storage.HistoryRow;
import yandex.muratov.translator.storage.api.OnChangeStorage;
import yandex.muratov.translator.storage.api.Result;
import yandex.muratov.translator.storage.api.StorageOperations;
import yandex.muratov.translator.ui.ContextHolderFragment;
import yandex.muratov.translator.ui.OneLineSearchBar;
import yandex.muratov.translator.util.ListsUtil;

import static yandex.muratov.translator.util.AndroidUtil.findViewById;

public abstract class BasePageFragment extends Fragment {
    private static String TAG = BasePageFragment.class.getSimpleName();

    protected OneLineSearchBar searchBar;
    protected ListView listOfElements;

    protected OnChangeStorage onChangeStorage;
    protected ContextHolderFragment contextHolder;

    protected StoredRecordsAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page_bookmark_screen, container, false);
        onChangeStorage = initOnChangeStorage();
        contextHolder = ContextHolderFragment.findContextFragment(this.getParentFragment());
        searchBar = initSearchBar(view);
        adapter = initAdapter(getContext());
        listOfElements = initListOfElements(view);
        return view;
    }

    @Override
    public void onViewCreated(View baseView, @Nullable Bundle savedInstanceState) {


    }


    @Override
    public void onResume() {
        super.onResume();
        contextHolder
                .getHistoryContext()
                .getConnector()
                .subscribe(onChangeStorage);
        contextHolder.getHistoryContext().getConnector().getByPredicate(getSearchPredicate(""));
    }

    @Override
    public void onPause() {
        super.onPause();
        contextHolder.getHistoryContext().getConnector().unSubscribe();
    }

    protected StoredRecordsAdapter initAdapter(Context appContext) {
        ArrayList<HistoryRow> dataset = new ArrayList<>();
        dataset.add(HistoryRow.createWithNewTimestamp("1", "2", "3", false));
        return new StoredRecordsAdapter(appContext, dataset);
    }

    private OnChangeStorage initOnChangeStorage() {
        return new OnChangeStorage() {
            @Override
            public void onInsertCallback(HistoryRow actual) {

            }

            @Override
            public void onRemoveByPredicate(Result<HistoryRow> removed) {

            }

            @Override
            public void onGetByPredicate(Result<HistoryRow> result) {
                Log.d(TAG, String.format("onGetByPredicate: size=%s", result.size()));

                List<HistoryRow> fetch = ListsUtil.fetch(result.values());
                adapter.insert(fetch);
            }
        };
    }

    protected TextWatcher initSearchBarTextWatcher() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                contextHolder
                        .getHistoryContext()
                        .getConnector()
                        .getByPredicate(getSearchPredicate(s.toString()));
            }
        };
    }

    protected abstract StorageOperations.Predicate<HistoryRow> getSearchPredicate(final String s);

    @Override
    public void onStop() {
        super.onStop();
        contextHolder.getHistoryContext().getConnector().unSubscribe();
    }

    private OneLineSearchBar initSearchBar(View rootView) {
        OneLineSearchBar viewById = findViewById(rootView, R.id.bar_search);
        viewById.getQueryLine().addTextChangedListener(initSearchBarTextWatcher());
        return viewById;
    }

    private ListView initListOfElements(View rootView) {
        ListView listOfRecords = findViewById(rootView, R.id.list_of_translations);
        listOfRecords.setAdapter(adapter);
        return listOfRecords;
    }

}
