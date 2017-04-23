package yandex.muratov.translator.storage.api;


import yandex.muratov.translator.storage.HistoryRow;

public interface StorageOperations {
    void putInHistory(HistoryRow actual);

    void setFavorite(HistoryRow row, boolean favorite);

    void removeByPredicate(Predicate<HistoryRow> predicate);

    void getByPredicate(Predicate<HistoryRow> predicate);

    interface Predicate<T> {
        boolean apply(T value);
    }
}
