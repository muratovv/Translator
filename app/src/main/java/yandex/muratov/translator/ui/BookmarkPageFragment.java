package yandex.muratov.translator.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class BookmarkPageFragment extends BasePageFragment {
    private static String TAG = BookmarkPageFragment.class.getSimpleName();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public static BookmarkPageFragment getInstance() {
        Log.d(TAG, "getInstance: ");
        return new BookmarkPageFragment();
    }
}
