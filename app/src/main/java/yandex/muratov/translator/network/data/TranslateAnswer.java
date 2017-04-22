package yandex.muratov.translator.network.data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * @author @muratovv
 * @date 20.04.17
 */
public class TranslateAnswer {
    private int code;
    private String lang = "";
    @SerializedName("text")
    private List<String> texts = new ArrayList<>();


    public List<String> getTexts() {
        return texts;
    }

    public int getCode() {
        return code;
    }

    public String getLanguage() {
        return lang;
    }

    public TranslateAnswer setCode(int code) {
        this.code = code;
        return this;
    }
}

