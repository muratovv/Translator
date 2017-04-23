package yandex.muratov.translator.storage.api;

import yandex.muratov.translator.storage.HistoryRow;

public interface OnChangeStorage {
    void onInsertCallback(HistoryRow actual);

    void onRemoveByPredicate(Result<HistoryRow> removed);

    void onGetByPredicate(Result<HistoryRow> result);
}
