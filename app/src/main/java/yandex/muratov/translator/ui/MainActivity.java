package yandex.muratov.translator.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import yandex.muratov.translator.R;
import yandex.muratov.translator.ui.bookmarks.BookmarkScreenFragment;
import yandex.muratov.translator.ui.translator.TranslatorScreenFragment;

import static yandex.muratov.translator.util.PermissionUtil.requestInternet;

public class MainActivity extends AppCompatActivity {

    private static String TAG = MainActivity.class.getSimpleName();
    private static String FRAGMENT_TAG = "screen";

    private ContextHolderFragment fragment;

    private BottomNavigationView navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, String.format("OnCreate bundle: %s", savedInstanceState));
        setContentView(R.layout.activity_main);
        navigation = initNavigation();
        fragment = initContextFragment(getSupportFragmentManager());
        onRestoreInstanceState(savedInstanceState);
        requestInternet(this);
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.d(TAG, "onSaveInstanceState: ");
        super.onSaveInstanceState(outState);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.d(TAG, String.format("onRestoreInstanceState bundle: %s", savedInstanceState));
        if (savedInstanceState != null) {
            super.onRestoreInstanceState(savedInstanceState);

        }
        initFirstTime(savedInstanceState);
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
                                changeScreen(R.id.layout_screen, initTranslatorScreenFragment());
                                break;
                            case R.id.action_bookmarks_screen:
                                Log.d(TAG, "onClick: bookmarks");
                                changeScreen(R.id.layout_screen, new BookmarkScreenFragment());
                                break;
                        }
                        return true;
                    }
                });
        return navigationView;
    }

    @NonNull
    private TranslatorScreenFragment initTranslatorScreenFragment() {
        return new TranslatorScreenFragment();
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

    private static FragmentTransaction removePreviousFragment(FragmentManager manager,
                                                              FragmentTransaction transaction) {
        Fragment oldFragment = manager.findFragmentByTag(FRAGMENT_TAG);
        if (oldFragment != null)
            transaction.remove(oldFragment);
        return transaction;
    }

    private static ContextHolderFragment initContextFragment(FragmentManager fragmentManager) {
        ContextHolderFragment contextHolderFragment = new ContextHolderFragment();
        fragmentManager.beginTransaction()
                .add(contextHolderFragment, ContextHolderFragment.TAG)
                .commit();
        return contextHolderFragment;
    }


}
