package yandex.muratov.translator.translate.data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents entry of "def" key in Yandex Dictionary API
 */
@SuppressWarnings({"FieldCanBeLocal", "unused"})
public class DefinitionEntry {

    private String text = "";
    @SerializedName("pos")
    private String partOfSpeech;

    @SerializedName("tr")
    private List<TranslationEntry> translations = new ArrayList<>();

    public String getText() {
        return text;
    }

    public String getPartOfSpeech() {
        return partOfSpeech;
    }

    public List<TranslationEntry> getTranslations() {
        return translations;
    }
}
