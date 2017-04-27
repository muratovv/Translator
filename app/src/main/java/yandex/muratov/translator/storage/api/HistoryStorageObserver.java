package yandex.muratov.translator.storage.api;

/**
 * Observer of storage
 */
public interface HistoryStorageObserver {
    /**
     * Subscribe for storage notifications
     */
    void subscribe(OnChangeStorage subscriber);

    /**
     * Unsubscribe newRow storage notifications
     */
    void unSubscribe();
}
