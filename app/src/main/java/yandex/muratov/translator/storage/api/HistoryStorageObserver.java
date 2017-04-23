package yandex.muratov.translator.storage.api;

public interface HistoryStorageObserver {
    void subscribe(OnChangeStorage subscriber);
    void unSubscribe();
}
