package yandex.muratov.translator.storage.api;


import yandex.muratov.translator.storage.HistoryRow;

public interface StorageOperations {
    void insert(HistoryRow row);

    void removeByPredicate(Predicate<HistoryRow> predicate);

    void getByPredicate(Predicate<HistoryRow> predicate);

    interface Predicate<T> {
        boolean apply(T value);
    }
}
