package yandex.muratov.translator.translate.data;


import java.util.HashMap;
import java.util.Map;

import yandex.muratov.translator.R;

/**
 * Class contains useful information for process data errors
 */
public class DataCodes {

    /**
     * Valid code inside answers
     */
    public static final int VALID_ANSWER_CODE = 200;

    /**
     * Flag that {@link DataCodes#getResourceByError} not find error
     */
    public static final Integer OK_RESPONSE_CODE = 0;

    /**
     * Check correctness of {@link TranslateAnswer}
     */
    public static boolean isValid(TranslateAnswer answer) {
        return answer != null && answer.getCode() == VALID_ANSWER_CODE;
    }

    /**
     * Check correctness of {@link DictionaryAnswer}
     */

    public static boolean isValid(DictionaryAnswer answer) {
        return answer != null && answer.getCode() == VALID_ANSWER_CODE;
    }

    /**
     * @return code of error associated with {@link DataCodes#allDataErrors},
     * if there is not return {@link DataCodes#OK_RESPONSE_CODE}
     */
    public static Integer getResourceByError(TranslateAnswer answer) {
        if (isValid(answer))
            return OK_RESPONSE_CODE;
        Integer code = allDataErrors.get(answer.getCode());
        if (code == null) {
            return R.string.translation_api_error_unknown_network_error;
        } else {
            return code;
        }
    }

    /**
     * @return code of error associated with {@link DataCodes#allDataErrors},
     * if there is not return {@link DataCodes#OK_RESPONSE_CODE}
     */
    public static Integer getResourceByError(DictionaryAnswer answer) {
        if (isValid(answer))
            return OK_RESPONSE_CODE;
        Integer code = allDataErrors.get(answer.getCode());
        if (code == null) {
            return R.string.translation_api_error_unknown_network_error;
        } else {
            return code;
        }
    }

    /**
     * Contains all codes of errors for Yandex API
     */
    @SuppressWarnings("WeakerAccess")
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
