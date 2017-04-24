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

    private StoredRecordsAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page_bookmark_screen, container, false);

        contextHolder = ContextHolderFragment.findContextFragment(this.getParentFragment());
        adapter = initAdapter(getContext());
        listOfElements = initListOfElements(view);
        searchBar = initSearchBar(view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        onChangeStorage = initOnChangeStorage();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    protected StoredRecordsAdapter initAdapter(Context appContext) {
        Log.d(TAG, "initAdapter: ");
        ArrayList<HistoryRow> dataset = new ArrayList<>();
        return new StoredRecordsAdapter(appContext, dataset);
    }

    private OnChangeStorage initOnChangeStorage() {
        Log.d(TAG, String.format("bind adapter: adapter=%d", adapter.hashCode()));
        return new OnChangeStorage() {
            @Override
            public void onInsertCallback(HistoryRow actual) {

            }

            @Override
            public void onRemoveByPredicate(Result<HistoryRow> removed) {

            }

            @Override
            public void onGetByPredicate(Result<HistoryRow> result) {
                Log.d(TAG, String.format("onGetByPredicate: adapter=%d, size=%s", adapter.hashCode(), result.size()));

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
                Log.d(TAG, String.format("in bar: adapter=%d, list=%d", adapter.hashCode(), listOfElements.hashCode()));
                contextHolder
                        .getHistoryContext()
                        .getConnector()
                        .getByPredicate(getSearchPredicate(s.toString()));
            }
        };
    }

    protected abstract StorageOperations.Predicate<HistoryRow> getSearchPredicate(final String s);

    @Override
    public void onPause() {
        super.onPause();
        deactivate();
    }

    private OneLineSearchBar initSearchBar(View rootView) {
        OneLineSearchBar bar = findViewById(rootView, R.id.bar_search);
        bar.getQueryLine().addTextChangedListener(initSearchBarTextWatcher());
        Log.d(TAG, String.format("initSearchBar: hash %d", bar.hashCode()));
        return bar;
    }

    private ListView initListOfElements(View rootView) {
        ListView listOfRecords = findViewById(rootView, R.id.list_of_translations);
        listOfRecords.setAdapter(adapter);
        return listOfRecords;
    }

    public void activate() {
        Log.d(TAG, String.format("activate: %s", this.getClass().getSimpleName()));
        if (contextHolder != null &&
                contextHolder.getHistoryContext() != null &&
                contextHolder.getHistoryContext()
                        .getConnector() != null) {
            contextHolder
                    .getHistoryContext()
                    .getConnector()
                    .subscribe(onChangeStorage);
            contextHolder.getHistoryContext().getConnector().getByPredicate(getSearchPredicate(""));
        }
    }

    private void deactivate() {
        Log.d(TAG, String.format("deactivate: %s", this.getClass().getSimpleName()));
        contextHolder.getHistoryContext().getConnector().unSubscribe();
    }

}
