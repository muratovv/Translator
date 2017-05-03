package yandex.muratov.translator.storage.api;

import java.util.Set;

/**
 * Interface for internal access to Map based implementation of storage
 *
 * @param <T>
 */
public interface SetStorage<T> {

    /**
     * @param value object to insert
     * @return previous value
     */
    T put(T value);

    /**
     * @return previous value or null
     */
    T get(T value);

    /**
     * @param value for removing from storage
     */
    T remove(T value);

    /**
     * @return all values available in storage
     */
    Set<T> values();
}
