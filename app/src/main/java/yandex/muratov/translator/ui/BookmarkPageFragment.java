package yandex.muratov.translator.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import yandex.muratov.translator.R;

public class BookmarkPageFragment extends Fragment {
    private static String TAG = BookmarkPageFragment.class.getSimpleName();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.page_bookmark_screen, container, false);
    }

    public static BookmarkPageFragment getInstance() {
        Log.d(TAG, "getInstance: ");
        return new BookmarkPageFragment();
    }
}
