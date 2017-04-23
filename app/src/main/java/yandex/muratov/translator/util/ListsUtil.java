package yandex.muratov.translator.util;

import java.util.ArrayList;
import java.util.List;

import yandex.muratov.translator.network.data.MeanEntry;
import yandex.muratov.translator.network.data.SynonymEntry;

public class ListsUtil {
    public static <T> List<T> of(final T val, final List<T> arg) {
        ArrayList<T> ts = new ArrayList<>();
        ts.add(val);
        ts.addAll(arg);
        return ts;
    }

    public static List<String> extractSynonyms(List<SynonymEntry> synonyms) {
        List<String> result = new ArrayList<>();
        for (SynonymEntry synonymEntry : synonyms) {
            result.add(synonymEntry.getText());
        }
        return result;
    }

    public static List<String> extractMeans(List<MeanEntry> means) {
        List<String> result = new ArrayList<>();
        for (MeanEntry synonymEntry : means) {
            result.add(synonymEntry.getText());
        }
        return result;

    }
}
