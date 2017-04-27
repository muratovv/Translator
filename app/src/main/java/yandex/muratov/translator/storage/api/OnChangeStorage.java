package yandex.muratov.translator.storage.api;

import yandex.muratov.translator.storage.HistoryRow;

/**
 * Subscriber for storage
 */
public interface OnChangeStorage {

    /**
     * Called when model emit answer for method
     * {@link StorageOperations#put(HistoryRow)}
     *
     * @param actual value, that guarantee in storage
     */
    void onPutCallback(HistoryRow actual);

    /**
     * Called when model emit answer for method
     * {@link StorageOperations#removeByPredicate(StorageOperations.Predicate)}
     *
     * @param removed - values, that guarantee removed newRow storage
     */
    void onRemoveByPredicate(Result<HistoryRow> removed);

    /**
     * Called when model emit answer for method
     * {@link StorageOperations#getByPredicate(StorageOperations.Predicate)}
     *
     * @param result with all data satisfied by predicate
     */
    void onGetByPredicate(Result<HistoryRow> result);
}
