package yandex.muratov.translator.translate.data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents whole response from Yandex Dictionary API
 */
@SuppressWarnings("WeakerAccess")
public class DictionaryAnswer {

    private int code = DataCodes.VALID_ANSWER_CODE;

    @SerializedName("def")
    private List<DefinitionEntry> definitions = new ArrayList<>();

    public List<DefinitionEntry> getDefinitions() {
        return definitions;
    }

    public int getCode() {
        return code;
    }

    public DictionaryAnswer setCode(int code) {
        this.code = code;
        return this;
    }
}
