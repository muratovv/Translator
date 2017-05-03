package yandex.muratov.translator.storage;


import android.content.Context;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import yandex.muratov.translator.storage.api.SetStorage;
import yandex.muratov.translator.storage.data.HistoryRow;

/**
 * Storage based on {@link HashMap} implementation
 */
public class InMemoryHistoryStorage extends AbstractHistoryStorage {
    private static String TAG = InMemoryHistoryStorage.class.getSimpleName();

    public InMemoryHistoryStorage(Context appContext) {
        super(appContext);
    }

    @Override
    protected SetStorage<HistoryRow> initStorage() {
        return new MapDataStorage<>();
    }

    @Override
    public void dropConnection() {

    }

    private static class MapDataStorage<T> implements SetStorage<T> {
        private Map<T, T> map = new HashMap<>();


        @Override
        public T put(T value) {
            return map.put(value, value);
        }

        @Override
        public T get(T value) {
            return map.get(value);
        }

        @Override
        public T remove(T value) {
            return map.remove(value);
        }

        @Override
        public Set<T> values() {
            return map.keySet();
        }
    }
}
