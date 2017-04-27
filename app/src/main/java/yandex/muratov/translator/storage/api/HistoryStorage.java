package yandex.muratov.translator.storage.api;

public interface HistoryStorage extends StorageOperations {

    /**
     * close connection to storage
     */
    void dropConnection();
}
