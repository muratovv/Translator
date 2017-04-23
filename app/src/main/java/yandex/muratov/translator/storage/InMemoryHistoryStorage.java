package yandex.muratov.translator.storage;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import yandex.muratov.translator.storage.api.HistoryStorageModel;
import yandex.muratov.translator.storage.api.OnChangeStorage;
import yandex.muratov.translator.storage.api.Result;

public class InMemoryHistoryStorage implements HistoryStorageModel {

    private Set<HistoryRow> rows = new HashSet<>();
    private OnChangeStorage modelSubscriber;


    @Override
    public void insert(HistoryRow row) {
        rows.add(row);
        onInsertCallback(row);
    }

    @Override
    public void onInsertCallback(HistoryRow row) {
        if (modelSubscriber != null) {
            modelSubscriber.onInsertCallback(row);
        }
    }

    @Override
    public void removeByPredicate(Predicate<HistoryRow> predicate) {
        List<HistoryRow> removed = new ArrayList<>();
        for (HistoryRow row : rows) {
            if (predicate.apply(row)) {
                rows.remove(row);
                removed.add(row);
            }
        }
        onRemoveByPredicate(new InMemoryResult(removed));
    }


    @Override
    public void onRemoveByPredicate(Result<HistoryRow> removed) {
        if (modelSubscriber != null) {
            modelSubscriber.onRemoveByPredicate(removed);
        }
    }

    @Override
    public void getByPredicate(Predicate<HistoryRow> predicate) {
        List<HistoryRow> chosen = new ArrayList<>();
        for (HistoryRow row : rows) {
            if (predicate.apply(row)) {
                chosen.add(row);
            }
        }
        Collections.sort(chosen);
        Collections.reverse(chosen);
        onGetByPredicate(new InMemoryResult(chosen));
    }

    @Override
    public void dropConnection() {
    }

    @Override
    public void onGetByPredicate(Result<HistoryRow> result) {
        if (modelSubscriber != null) {
            modelSubscriber.onGetByPredicate(result);
        }
    }

    @Override
    public void subscribe(OnChangeStorage subscriber) {
        this.modelSubscriber = subscriber;
    }

    @Override
    public void unSubscribe() {
        this.modelSubscriber = null;
    }

    private static class InMemoryResult implements Result<HistoryRow> {

        private List<HistoryRow> result;

        public InMemoryResult(List<HistoryRow> result) {
            this.result = result;
        }

        @Override
        public int size() {
            return result.size();
        }

        @Override
        public Iterator<HistoryRow> values() {
            return result.iterator();
        }
    }
}
