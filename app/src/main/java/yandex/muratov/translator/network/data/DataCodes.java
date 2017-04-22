package yandex.muratov.translator.network.data;


import java.util.HashMap;
import java.util.Map;

import yandex.muratov.translator.R;

public class DataCodes {

    public static final int VALID_ANSWER_CODE = 200;
    public static final Integer OK_CODE = 0;

    public static boolean isValid(TranslateAnswer answer) {
        return answer != null && answer.getCode() == VALID_ANSWER_CODE;
    }

    public static boolean isValid(DictionaryAnswer answer) {
        return answer != null && answer.getCode() == VALID_ANSWER_CODE;
    }

    public static Integer getResourceByError(TranslateAnswer answer) {
        if (isValid(answer))
            return OK_CODE;
        Integer code = allDataErrors.get(answer.getCode());
        if (code == null) {
            return R.string.translation_api_error_unknown_network_error;
        } else {
            return code;
        }
    }

    public static Integer getResourceByError(DictionaryAnswer answer) {
        if (isValid(answer))
            return OK_CODE;
        Integer code = allDataErrors.get(answer.getCode());
        if (code == null) {
            return R.string.translation_api_error_unknown_network_error;
        } else {
            return code;
        }
    }

    public static Map<Integer, Integer> allDataErrors = new HashMap<Integer, Integer>() {
        private HashMap<Integer, Integer> inflate() {

            this.put(401, R.string.translation_api_error_invalid_api_key);
            this.put(402, R.string.translation_api_error_blocked_api_key);
            this.put(404, R.string.translation_api_error_exceed_daily_limit);
            this.put(413, R.string.translation_api_error_exceed_maximum_text_size);
            this.put(422, R.string.translation_api_error_text_cannot_be_translated);
            this.put(501, R.string.translation_api_error_translation_direction_not_supported);
            return this;
        }
    }.inflate();


}
