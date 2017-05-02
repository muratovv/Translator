package yandex.muratov.translator.storage;


import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import yandex.muratov.translator.storage.api.KeyValueStorage;
import yandex.muratov.translator.storage.data.HistoryRow;

/**
 * Storage based on {@link HashMap} implementation
 */
public class InMemoryHistoryStorage extends AbstractHistoryStorage {
    private static String TAG = InMemoryHistoryStorage.class.getSimpleName();

    @Override
    protected KeyValueStorage<HistoryRow> initStorage() {
        return new MapDataStorage<>();
    }

    @Override
    public void dropConnection() {

    }

    private static class MapDataStorage<T> implements KeyValueStorage<T> {
        private Map<T, T> map = new HashMap<>();


        @Override
        public T put(T key) {
            return map.put(key, key);
        }

        @Override
        public T get(T key) {
            return map.get(key);
        }

        @Override
        public T remove(T key) {
            return map.remove(key);
        }

        @Override
        public Set<T> values() {
            return map.keySet();
        }
    }
}
