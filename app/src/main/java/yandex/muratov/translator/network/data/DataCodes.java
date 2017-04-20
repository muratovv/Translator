package yandex.muratov.translator.network.data;


import java.util.HashMap;
import java.util.Map;

public class DataCodes {

    public static final int VALID_CODE = 200;
    public static final String NO_ERROR = "";

    public static boolean isValid(TranslateAnswer answer) {
        return answer != null && answer.getCode() == VALID_CODE;
    }

    public static boolean isValid(DictionaryAnswer answer) {
        return answer != null && answer.getCode() == VALID_CODE;
    }

    public static String getError(TranslateAnswer answer) {
        if (isValid(answer))
            return NO_ERROR;
        String s = allDataErrors.get(answer.getCode());
        if (s == null) {
            return addResourcePrefix("unknown_error");
        } else {
            return s;
        }
    }

    public static String getError(DictionaryAnswer answer) {
        if (isValid(answer))
            return NO_ERROR;
        String s = allDataErrors.get(answer.getCode());
        if (s == null) {
            return addResourcePrefix("unknown_error");
        } else {
            return s;
        }
    }

    public static Map<Integer, String> allDataErrors = new HashMap<Integer, String>() {
        private HashMap<Integer, String> inflate() {
            this.put(401, addResourcePrefix("invalid_api_key"));
            this.put(402, addResourcePrefix("blocked_api_key"));
            this.put(404, addResourcePrefix("exceed_daily_limit"));
            this.put(413, addResourcePrefix("exceed_maximum_text_size"));
            this.put(422, addResourcePrefix("text_cannot_be_translated"));
            this.put(501, addResourcePrefix("translation_direction_not_supported"));
            return this;
        }
    }.inflate();

    private static String addResourcePrefix(String text) {
        String prefix = "R.string.translation_api_error_";
        return prefix + text;
    }

}
