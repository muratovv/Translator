package yandex.muratov.translator.storage;


import android.content.Context;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.paperdb.Book;
import io.paperdb.Paper;
import yandex.muratov.translator.storage.api.SetStorage;
import yandex.muratov.translator.storage.data.HistoryRow;

import static yandex.muratov.translator.storage.data.HistoryRowSerializations.deserialize;
import static yandex.muratov.translator.storage.data.HistoryRowSerializations.serialize;

public class DbHistoryStorage extends AbstractHistoryStorage {

    public DbHistoryStorage(Context appContext) {
        super(appContext);
    }

    @Override
    protected SetStorage<HistoryRow> initStorage() {
        return new PaperStorage(appContext);
    }

    @Override
    public void dropConnection() {
    }

    private static class PaperStorage implements SetStorage<HistoryRow> {

        private static String BOOK_TAG = "bookmarks";
        private Book book;

        public PaperStorage(Context appContext) {
            Paper.init(appContext);
            book = Paper.book(BOOK_TAG);
        }

        @Override
        public HistoryRow put(HistoryRow value) {
            String serialized = serialize(value);
            HistoryRow previous = deserialize((String) book.read(serialized));
            book.write(serialized, serialized);
            return previous;
        }

        @Override
        public HistoryRow get(HistoryRow value) {
            return deserialize((String) book.read(serialize(value)));
        }

        @Override
        public HistoryRow remove(HistoryRow key) {
            HistoryRow value = get(key);
            book.delete(serialize(key));
            return value;
        }

        @Override
        public Set<HistoryRow> values() {
            List<String> allKeys = book.getAllKeys();
            List<HistoryRow> result = new ArrayList<>();
            for (String key : allKeys) {
                result.add(deserialize(key));
            }
            return new HashSet<>(result);
        }
    }
}
