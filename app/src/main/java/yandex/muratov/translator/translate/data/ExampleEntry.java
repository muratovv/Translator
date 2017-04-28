package yandex.muratov.translator.translate.data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents "ex" key in response of Yandex Dictionary API
 */
@SuppressWarnings("FieldCanBeLocal")
public class ExampleEntry {
    private String text = "";
    @SerializedName("tr")
    private List<LightTranslationEntry> translations = new ArrayList<>();

    public String getText() {
        return text;
    }

    public List<LightTranslationEntry> getTranslations() {
        return translations;
    }
}
