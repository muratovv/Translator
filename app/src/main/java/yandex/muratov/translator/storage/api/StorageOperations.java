package yandex.muratov.translator.storage.api;


import yandex.muratov.translator.storage.HistoryRow;

/**
 * Operations for manipulating data
 */
public interface StorageOperations {

    /**
     * @param value for insert
     */
    void put(HistoryRow value);

    /**
     * Set favorite flag
     *
     * @param row      that available in storage
     * @param favorite flag
     */
    void setFavorite(HistoryRow row, boolean favorite);

    /**
     * Remove all rows, that satisfy by predicate
     */
    void removeByPredicate(Predicate<HistoryRow> predicate);

    /**
     * Get all rows, that satisfy by predicate
     */
    void getByPredicate(Predicate<HistoryRow> predicate);

    interface Predicate<T> {
        boolean apply(T value);
    }
}
