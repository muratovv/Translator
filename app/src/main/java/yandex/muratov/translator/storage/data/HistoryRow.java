package yandex.muratov.translator.storage.data;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.Date;

/**
 * Row that contains in history. Rows are immutable.
 */
public class HistoryRow implements Serializable, Comparable<HistoryRow> {

    /**
     * Text on source language
     */
    private String sourceText;

    /**
     * Text on target language
     */
    private String translationText;

    /**
     * Language of translation. Contains pair of languages. Ex: "fr-en"
     */
    private String rawLang;

    /**
     * Flag that shows, is this row in favorite list
     */
    private boolean inFavorites;

    /**
     * Timestamp of insertion
     */
    private long insertionTimestamp;

    private HistoryRow(String sourceText, String translationText,
                       boolean inFavorites,
                       String rawLang,
                       long insertionTimestamp) {
        this.sourceText = sourceText.trim();
        this.translationText = translationText;
        this.inFavorites = inFavorites;
        this.rawLang = rawLang;
        this.insertionTimestamp = insertionTimestamp;
    }

    /**
     * Create {@link HistoryRow} with all useful fields
     */
    private static HistoryRow createWithTimestamp(String sourceText, String translationText,
                                                  String rawLang,
                                                  boolean inFavorites) {
        return new HistoryRow(sourceText, translationText,
                inFavorites,
                rawLang,
                getCurrentTimestamp());
    }

    /**
     * Copy {@link HistoryRow} with update timestamp value
     */
    public static HistoryRow copyWithNewTimestamp(HistoryRow value) {
        return createWithTimestamp(value.getSourceText(), value.getTranslationText(),
                value.getRawLang(), value.inFavorites());
    }

    /**
     * Create new {@link HistoryRow} with inFavorite flag eq false and current timestamp
     */
    public static HistoryRow newRow(String sourceText, String translationText,
                                    String rawLang) {
        return createWithTimestamp(sourceText, translationText,
                rawLang, false);
    }

    public static HistoryRow createWithFavorites(HistoryRow row, boolean favorites) {
        return createWithTimestamp(row.sourceText, row.getTranslationText(),
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

    public boolean inFavorites() {
        return inFavorites;
    }

    public String getRawLang() {
        return rawLang;
    }

    /**
     * Comparison between {@link HistoryRow} based only on insertion timestamp
     */
    @Override
    public int compareTo(@NonNull HistoryRow o) {
        return (int) (insertionTimestamp - o.insertionTimestamp);
    }

    /**
     * Equal compare equality of all fields exclude favorite flag and timestamp
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HistoryRow that = (HistoryRow) o;
        if (!getSourceText().equals(that.getSourceText())) return false;
        if (!getTranslationText().equals(that.getTranslationText())) return false;
        return getRawLang().equals(that.getRawLang());

    }

    /**
     * HashCode based on all fields exclude favorite flag and timestamp
     */
    @Override
    public int hashCode() {
        int result = getSourceText().hashCode();
        result = 31 * result + getTranslationText().hashCode();
        result = 31 * result + getRawLang().hashCode();
        return result;
    }
}
