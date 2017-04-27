package yandex.muratov.translator.storage;

import yandex.muratov.translator.storage.api.HistoryStorage;
import yandex.muratov.translator.storage.api.HistoryStorageModel;
import yandex.muratov.translator.storage.api.HistoryStorageObserver;
import yandex.muratov.translator.storage.api.OnChangeStorage;
import yandex.muratov.translator.storage.api.Result;

/**
 * Class resolves access to history storage
 */
public class StorageController implements HistoryStorageObserver, HistoryStorage {
    private HistoryStorageModel storage;
    private OnChangeStorage uiSubscriber;

    public StorageController(HistoryStorageModel storage) {
        this.storage = storage;
        this.storage.subscribe(new OnChangeStorage() {
            @Override
            public void onPutCallback(HistoryRow actual) {
                if (uiSubscriber != null && actual != null)
                    uiSubscriber.onPutCallback(actual);
            }

            @Override
            public void onRemoveByPredicate(Result<HistoryRow> removed) {
                if (uiSubscriber != null && removed != null)
                    uiSubscriber.onRemoveByPredicate(removed);
            }

            @Override
            public void onGetByPredicate(Result<HistoryRow> result) {
                if (uiSubscriber != null && result != null)
                    uiSubscriber.onGetByPredicate(result);
            }
        });
    }

    @Override
    public void subscribe(OnChangeStorage subscriber) {
        this.uiSubscriber = subscriber;
    }

    @Override
    public void unSubscribe() {
        this.uiSubscriber = null;
    }

    @Override
    public void put(HistoryRow value) {
        if (value != null) {
            storage.put(value);
        }
    }

    @Override
    public void setFavorite(HistoryRow row, boolean favorite) {
        storage.setFavorite(row, favorite);
    }

    @Override
    public void removeByPredicate(Predicate<HistoryRow> predicate) {
        if (predicate != null) {
            storage.removeByPredicate(predicate);
        }
    }

    @Override
    public void getByPredicate(Predicate<HistoryRow> predicate) {
        if (predicate != null) {
            storage.getByPredicate(predicate);
        }
    }

    @Override
    public void dropConnection() {
        storage.dropConnection();
    }
}
