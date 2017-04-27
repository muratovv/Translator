package yandex.muratov.translator.storage.api;

/**
 * Interface of storage that available observe requests to storage
 */
public interface HistoryStorageModel extends HistoryStorage, HistoryStorageObserver, OnChangeStorage {
}
