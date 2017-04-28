package yandex.muratov.translator.translate.data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import yandex.muratov.translator.util.StringUtil;

/**
 * Class represents response from {@link yandex.muratov.translator.translate.api.TranslateApi}
 */
@SuppressWarnings("WeakerAccess")
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

    @Override
    public String toString() {
        return "TranslateAnswer{" +
                "code=" + code +
                ", lang='" + lang + '\'' +
                ", texts=" + StringUtil.join(", ", texts) +
                '}';
    }
}

