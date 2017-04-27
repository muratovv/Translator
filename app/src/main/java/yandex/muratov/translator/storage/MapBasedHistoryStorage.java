package yandex.muratov.translator.storage;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import yandex.muratov.translator.storage.api.HistoryStorageModel;
import yandex.muratov.translator.storage.api.OnChangeStorage;
import yandex.muratov.translator.storage.api.Result;
import yandex.muratov.translator.storage.api.StorageOperations;


public abstract class MapBasedHistoryStorage implements HistoryStorageModel {
    private static String TAG = MapBasedHistoryStorage.class.getSimpleName();

    private DataStorage<HistoryRow> storage;
    private OnChangeStorage modelSubscriber;

    public MapBasedHistoryStorage() {
        storage = initStorage();
    }

    protected abstract DataStorage<HistoryRow> initStorage();


    @Override
    public void putInHistory(HistoryRow actual) {
        HistoryRow oldValue = storage.get(actual);
        if (oldValue == null) {
            storage.put(actual);
            onInsertCallback(actual);
        } else {
            storage.remove(oldValue);
            HistoryRow withNewTimestamp = HistoryRow.createWithNewTimestamp(oldValue);
            storage.put(withNewTimestamp);
            onInsertCallback(withNewTimestamp);
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
            onInsertCallback(newRow);
        }
    }

    @Override
    public void onInsertCallback(HistoryRow actual) {
        if (modelSubscriber != null) {
            modelSubscriber.onInsertCallback(actual);
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
    public void onRemoveByPredicate(Result<HistoryRow> removed) {
        if (modelSubscriber != null) {
            modelSubscriber.onRemoveByPredicate(removed);
        }
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
    public void dropConnection() {
    }

    @Override
    public void onGetByPredicate(Result<HistoryRow> result) {
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


    public interface DataStorage<T> {
        T put(T key);

        T get(T key);

        T remove(T key);

        Set<T> values();
    }
}