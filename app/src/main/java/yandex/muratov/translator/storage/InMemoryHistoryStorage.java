package yandex.muratov.translator.storage;


import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Storage based on {@link HashMap} implementation
 */
public class InMemoryHistoryStorage extends MapBasedHistoryStorage {
    private static String TAG = InMemoryHistoryStorage.class.getSimpleName();

    @Override
    protected DataStorage<HistoryRow> initStorage() {
        return new MapDataStorage<>();
    }


    private static class MapDataStorage<T> implements DataStorage<T> {
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
