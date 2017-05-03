package yandex.muratov.translator.storage;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import yandex.muratov.translator.storage.api.HistoryStorageModel;
import yandex.muratov.translator.storage.api.SetStorage;
import yandex.muratov.translator.storage.api.OnChangeStorage;
import yandex.muratov.translator.storage.api.Result;
import yandex.muratov.translator.storage.api.StorageOperations;
import yandex.muratov.translator.storage.data.HistoryRow;


/**
 * Storage based on key value structure
 */
public abstract class AbstractHistoryStorage implements HistoryStorageModel {
    private static String TAG = AbstractHistoryStorage.class.getSimpleName();

    private SetStorage<HistoryRow> storage;
    private OnChangeStorage modelSubscriber;

    protected Context appContext;

    public AbstractHistoryStorage(Context appContext) {
        this.appContext = appContext;
        storage = initStorage();
    }

    protected abstract SetStorage<HistoryRow> initStorage();


    @Override
    public void put(HistoryRow value) {
        HistoryRow oldValue = storage.get(value);
        if (oldValue == null) {
            storage.put(value);
            onPutCallback(value);
        } else {
            storage.remove(oldValue);
            HistoryRow withNewTimestamp = HistoryRow.copyWithNewTimestamp(oldValue);
            storage.put(withNewTimestamp);
            onPutCallback(withNewTimestamp);
        }
    }

    @Override
    public void setFavorite(HistoryRow row, boolean favorite) {
        HistoryRow historyRow = storage.get(row);
        if (historyRow != null && historyRow.inFavorites() != favorite) {
            HistoryRow oldRow = storage.remove(historyRow);
            HistoryRow newRow = HistoryRow.createWithFavorites(oldRow, favorite);
            storage.put(newRow);
            Log.d(TAG, String.format("setFavorite: %s for %s", favorite, newRow.getSourceText()));
            onPutCallback(newRow);
        }
    }

    @Override
    public void removeByPredicate(StorageOperations.Predicate<HistoryRow> predicate) {
        List<HistoryRow> removed = new ArrayList<>();
        for (HistoryRow row : storage.values()) {
            if (predicate.apply(row)) {
                storage.remove(row);
                removed.add(row);
            }
        }
        onRemoveByPredicate(new InMemoryResult(removed));
    }

    @Override
    public void getByPredicate(StorageOperations.Predicate<HistoryRow> predicate) {
        List<HistoryRow> chosen = new ArrayList<>();
        for (HistoryRow row : storage.values()) {
            if (predicate.apply(row)) {
                chosen.add(row);
            }
        }
        Collections.sort(chosen);
        Collections.reverse(chosen);
        onGetByPredicate(new InMemoryResult(chosen));
    }


    @Override
    public abstract void dropConnection();

    private void onPutCallback(HistoryRow actual) {
        if (modelSubscriber != null) {
            modelSubscriber.onPutCallback(actual);
        }
    }

    private void onRemoveByPredicate(Result<HistoryRow> removed) {
        if (modelSubscriber != null) {
            modelSubscriber.onRemoveByPredicate(removed);
        }
    }

    private void onGetByPredicate(Result<HistoryRow> result) {
        if (modelSubscriber != null) {
            modelSubscriber.onGetByPredicate(result);
        }
    }

    @Override
    public void subscribe(OnChangeStorage subscriber) {
        this.modelSubscriber = subscriber;
    }

    @Override
    public void unSubscribe() {
        this.modelSubscriber = null;
    }

    /**
     * Cursor of results for in-memory storage
     */
    private static class InMemoryResult implements Result<HistoryRow> {

        private List<HistoryRow> result;

        public InMemoryResult(List<HistoryRow> result) {
            this.result = result;
        }

        @Override
        public int size() {
            return result.size();
        }

        @Override
        public Iterator<HistoryRow> values() {
            return result.iterator();
        }
    }
}
