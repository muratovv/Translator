package yandex.muratov.translator;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import yandex.muratov.translator.ui.SettingsScreenFragment;
import yandex.muratov.translator.ui.bookmarks.BookmarkScreenFragment;
import yandex.muratov.translator.ui.translator.TranslatorScreenFragment;

public class MainActivity extends AppCompatActivity {

    private static String TAG = MainActivity.class.getSimpleName();
    private static String FRAGMENT_TAG = "screen";

    private BottomNavigationView navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, String.format("OnCreate bundle: %s", savedInstanceState));
        setContentView(R.layout.activity_main);
        navigation = initNavigation();
        onRestoreInstanceState(savedInstanceState);
    }

    private BottomNavigationView initNavigation() {
        BottomNavigationView navigationView = ((BottomNavigationView) findViewById(R.id.bar_navigation));
        navigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_translate_screen:
                                Log.d(TAG, "onClick: translate");
                                changeScreen(R.id.layout_screen, new TranslatorScreenFragment());
                                break;
                            case R.id.action_bookmarks_screen:
                                Log.d(TAG, "onClick: bookmarks");
                                changeScreen(R.id.layout_screen, new BookmarkScreenFragment());
                                break;
                            case R.id.action_preference_screen:
                                Log.d(TAG, "onClick: preferences");
                                changeScreen(R.id.layout_screen, new SettingsScreenFragment());
                                break;
                        }
                        return true;
                    }
                });
        return navigationView;
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.d(TAG, String.format("onRestoreInstanceState bundle: %s", savedInstanceState));
        if (savedInstanceState != null) {
            super.onRestoreInstanceState(savedInstanceState);
        }
        initFirstTime(savedInstanceState);
    }

    private void initFirstTime(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            navigation.setSelectedItemId(R.id.action_translate_screen);
        }
    }

    private void changeScreen(int layout, Fragment fragment) {

        FragmentManager fragmentManager = getSupportFragmentManager();
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
    protected void onSaveInstanceState(Bundle outState) {
        Log.d(TAG, "onSaveInstanceState: ");
        super.onSaveInstanceState(outState);
    }
}
