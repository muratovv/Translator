package yandex.muratov.translator.storage.api;

import java.util.Iterator;

public interface Result<T> {
    int size();

    Iterator<T> values();
}
