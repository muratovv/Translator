package yandex.muratov.translator.storage;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.Date;

import yandex.muratov.translator.network.data.Language;

public class HistoryRow implements Serializable, Comparable<HistoryRow> {
    private String sourceText;
    private String translationText;
    private boolean inFavorites;
    private Language sourceLanguage;
    private Language targetLanguage;
    private long insertionTimestamp;

    private HistoryRow(String sourceText, String translationText,
                       boolean inFavorites,
                       Language sourceLanguage, Language targetLanguage,
                       long insertionTimestamp) {
        this.sourceText = sourceText;
        this.translationText = translationText;
        this.inFavorites = inFavorites;
        this.sourceLanguage = sourceLanguage;
        this.targetLanguage = targetLanguage;
        this.insertionTimestamp = insertionTimestamp;
    }

    public static HistoryRow create(String sourceText, String translationText,
                                    boolean inFavorites,
                                    Language sourceLanguage, Language targetLanguage) {
        return new HistoryRow(sourceText, translationText,
                inFavorites,
                sourceLanguage, targetLanguage,
                getCurrentTimestamp());
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

    public boolean isInFavorites() {
        return inFavorites;
    }

    public Language getSourceLanguage() {
        return sourceLanguage;
    }

    public Language getTargetLanguage() {
        return targetLanguage;
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

        if (isInFavorites() != that.isInFavorites()) return false;
        if (insertionTimestamp != that.insertionTimestamp) return false;
        if (!getSourceText().equals(that.getSourceText())) return false;
        if (!getTranslationText().equals(that.getTranslationText())) return false;
        if (!getSourceLanguage().equals(that.getSourceLanguage())) return false;
        return getTargetLanguage().equals(that.getTargetLanguage());

    }

    @Override
    public int hashCode() {
        int result = getSourceText().hashCode();
        result = 31 * result + getTranslationText().hashCode();
        result = 31 * result + (isInFavorites() ? 1 : 0);
        result = 31 * result + getSourceLanguage().hashCode();
        result = 31 * result + getTargetLanguage().hashCode();
        result = 31 * result + (int) (insertionTimestamp ^ (insertionTimestamp >>> 32));
        return result;
    }
}
