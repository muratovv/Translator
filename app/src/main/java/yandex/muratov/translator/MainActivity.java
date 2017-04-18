package yandex.muratov.translator;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import yandex.muratov.translator.ui.BookmarkFragment;
import yandex.muratov.translator.ui.TranslatorFragment;

public class MainActivity extends AppCompatActivity {

    private static String TAG = "MAIN";
    private static String FRAGMENT_TAG = "screen";

    private ImageButton translate;
    private ImageButton bookmarks;
    private ImageButton preference;

    private Fragment activeFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        translate = initTranslateButton();

        bookmarks = initBookmarksButton();
        preference = ((ImageButton) findViewById(R.id.button_preference_screen));
    }

    private ImageButton initTranslateButton() {
        ImageButton translate = ((ImageButton) findViewById(R.id.button_translate_screen));
        translate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: translate");
                changeScreen(R.id.layout_screen, new TranslatorFragment());
            }
        });
        return translate;
    }

    private ImageButton initBookmarksButton() {
        ImageButton bookmarks = ((ImageButton) findViewById(R.id.button_bookmarks_screen));
        bookmarks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: bookmarks");
                changeScreen(R.id.layout_screen, new BookmarkFragment());
            }
        });
        return bookmarks;
    }

    private void changeScreen(int layout, Fragment fragment) {

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        removePreviousFragment(fragmentManager, transaction)
                .add(layout, fragment, FRAGMENT_TAG)
                .commit();
    }

    private FragmentTransaction removePreviousFragment(FragmentManager manager, FragmentTransaction transaction) {
        Fragment oldFragment = manager.findFragmentByTag(FRAGMENT_TAG);
        if (oldFragment != null)
            transaction.remove(oldFragment);
        return transaction;
    }

    @Override
    protected void onResume() {
        translate.callOnClick();
        super.onResume();
    }
}
