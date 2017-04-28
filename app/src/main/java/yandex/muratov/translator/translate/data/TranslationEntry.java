package yandex.muratov.translator.translate.data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Class represents key "tr" in Yandex Dictionary API
 */
@SuppressWarnings("ALL")
public class TranslationEntry {
    private String text = "";

    @SerializedName("pos")
    private String partOfSpeech = "";

    @SerializedName("syn")
    private List<SynonymEntry> synonyms = new ArrayList<>();

    @SerializedName("mean")
    private List<MeanEntry> means = new ArrayList<>();

    @SerializedName("ex")
    private List<ExampleEntry> examples = new ArrayList<>();

    public String getText() {
        return text;
    }

    public String getPartOfSpeech() {
        return partOfSpeech;
    }

    public List<SynonymEntry> getSynonyms() {
        return synonyms;
    }

    public List<MeanEntry> getMeans() {
        return means;
    }

    public List<ExampleEntry> getExamples() {
        return examples;
    }
}
