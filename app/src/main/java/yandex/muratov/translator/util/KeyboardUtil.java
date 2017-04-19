package yandex.muratov.translator.util;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;


public class KeyboardUtil {

    public static boolean showKeyboard(Context context, EditText target) {
        if (context == null || target == null) {
            return false;
        }
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);

        ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE)).showSoftInput(target, InputMethodManager.SHOW_FORCED);

        boolean didShowKeyboard = imm.showSoftInput(target, InputMethodManager.SHOW_FORCED);
        if (!didShowKeyboard) {
            didShowKeyboard = ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE)).showSoftInput(target, InputMethodManager.SHOW_FORCED);
        }
        return didShowKeyboard;
    }

}
