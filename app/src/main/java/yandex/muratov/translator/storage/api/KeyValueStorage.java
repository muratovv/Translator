package yandex.muratov.translator.storage.api;

import java.util.Set;

/**
 * Interface for internal access to Map based implementation of storage
 *
 * @param <T>
 */
public interface KeyValueStorage<T> {

    /**
     * @param key object to insert
     * @return previous value
     */
    T put(T key);

    /**
     * @return previous object for this key
     */
    T get(T key);

    /**
     * @param key for removing from storage
     * @return
     */
    T remove(T key);

    /**
     * @return all values available in storage
     */
    Set<T> values();
}
