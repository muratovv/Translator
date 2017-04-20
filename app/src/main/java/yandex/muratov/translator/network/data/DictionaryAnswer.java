package yandex.muratov.translator.network.data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * @author @muratovv
 * @date 20.04.17
 */
public class DictionaryAnswer {

    private int code = DataCodes.VALID_CODE;

    @SerializedName("def")
    private List<DefinitionEntry> definitions = new ArrayList<>();

    public List<DefinitionEntry> getDefinitions() {
        return definitions;
    }

    public int getCode() {
        return code;
    }
}
