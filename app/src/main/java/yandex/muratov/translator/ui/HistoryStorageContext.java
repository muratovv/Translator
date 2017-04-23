package yandex.muratov.translator.ui;

import yandex.muratov.translator.storage.StorageToUIConnector;

public class HistoryStorageContext {
    private StorageToUIConnector storageToUIConnector;

    public HistoryStorageContext(StorageToUIConnector storageToUIConnector) {
        this.storageToUIConnector = storageToUIConnector;
    }

    public StorageToUIConnector getConnector() {
        return storageToUIConnector;
    }
}
