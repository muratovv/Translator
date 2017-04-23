package yandex.muratov.translator.storage;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.Date;

public class HistoryRow implements Serializable, Comparable<HistoryRow> {
    private String sourceText;
    private String translationText;
    private String rawLang;
    private boolean inFavorites;
    private long insertionTimestamp;

    private HistoryRow(String sourceText, String translationText,
                       boolean inFavorites,
                       String rawLang,
                       long insertionTimestamp) {
        this.sourceText = sourceText;
        this.translationText = translationText;
        this.inFavorites = inFavorites;
        this.rawLang = rawLang;
        this.insertionTimestamp = insertionTimestamp;
    }

    public static HistoryRow createWithNewTimestamp(String sourceText, String translationText,
                                                    String rawLang,
                                                    boolean inFavorites) {
        return new HistoryRow(sourceText, translationText,
                inFavorites,
                rawLang,
                getCurrentTimestamp());
    }

    public static HistoryRow from(String sourceText, String translationText,
                                  String rawLang, long timeStamp) {
        return new HistoryRow(sourceText, translationText,
                false,
                rawLang,
                timeStamp);
    }

    public static HistoryRow createWithFavorites(HistoryRow row, boolean favorites) {
        return createWithNewTimestamp(row.sourceText, row.getTranslationText(),
                row.getRawLang(), favorites);
    }

    private static long getCurrentTimestamp() {
        return new Date().getTime();
    }

    public String getSourceText() {
        return sourceText;
    }

    public String getTranslationText() {
        return translationText;
    }

    public boolean isFavorites() {
        return inFavorites;
    }


    public String getRawLang() {
        return rawLang;
    }

    @Override
    public int compareTo(@NonNull HistoryRow o) {
        return (int) (insertionTimestamp - o.insertionTimestamp);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HistoryRow that = (HistoryRow) o;
        if (!getSourceText().equals(that.getSourceText())) return false;
        if (!getTranslationText().equals(that.getTranslationText())) return false;
        return getRawLang().equals(that.getRawLang());

    }

    @Override
    public int hashCode() {
        int result = getSourceText().hashCode();
        result = 31 * result + getTranslationText().hashCode();
        result = 31 * result + getRawLang().hashCode();
        return result;
    }

    public long getInsertionTimestamp() {
        return insertionTimestamp;
    }
}
