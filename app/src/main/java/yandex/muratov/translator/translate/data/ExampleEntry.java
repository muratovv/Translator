package yandex.muratov.translator.translate.data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * @author @muratovv
 * @date 20.04.17
 */
public class ExampleEntry {
    private String                 text = "";
    @SerializedName("tr")
    private List<LightTranslationEntry> translations = new ArrayList<>();

    public String getText() {
        return text;
    }

    public List<LightTranslationEntry> getTranslations() {
        return translations;
    }
}
