package yandex.muratov.translator.util;

import android.support.annotation.IdRes;
import android.view.View;

import yandex.muratov.translator.translate.data.Language;


/**
 * Utility class for android useful methods
 */
public class AndroidUtil {

    /**
     * Static method find view, with cast
     */
    @SuppressWarnings("unchecked")
    public static <T> T findViewById(View v, @IdRes int id) {
        return ((T) v.findViewById(id));
    }

    /**
     * Class for organization OnItemClick for {@link android.support.v7.widget.RecyclerView}
     */
    public abstract static class OnRecyclerViewItemClickListener {
        public abstract void onClick(Language language);
    }
}
