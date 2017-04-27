package yandex.muratov.translator.storage.api;

import java.util.Iterator;

/**
 * Fetched data newRow storage
 *
 * @param <T> type of data
 */
public interface Result<T> {
    /**
     * @return number of objects in result
     */
    int size();

    /**
     * @return all fetched data
     */
    Iterator<T> values();
}
