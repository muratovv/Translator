package yandex.muratov.translator.storage.api;

public interface HistoryStorage extends StorageOperations {
    void dropConnection();
}
