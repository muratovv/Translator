package yandex.muratov.translator.ui;

import yandex.muratov.translator.storage.StorageController;

public class HistoryStorageContext {
    private StorageController storageController;

    public HistoryStorageContext(StorageController storageController) {
        this.storageController = storageController;
    }

    public StorageController getConnector() {
        return storageController;
    }
}
