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

import java.io.File;

import yandex.muratov.translator.network.ConnectionBuilder;
import yandex.muratov.translator.network.NetworkUIConnector;
import yandex.muratov.translator.network.YandexDictionaryRepository;
import yandex.muratov.translator.network.YandexTranslateRepository;
import yandex.muratov.translator.network.YandexTranslator;
import yandex.muratov.translator.network.data.Language;
import yandex.muratov.translator.ui.SettingsScreenFragment;
import yandex.muratov.translator.ui.bookmarks.BookmarkScreenFragment;
import yandex.muratov.translator.ui.translator.TranslationContext;
import yandex.muratov.translator.ui.translator.TranslatorScreenFragment;

import static yandex.muratov.translator.util.PermissionUtil.requestInternet;

public class MainActivity extends AppCompatActivity {

    private static String TAG = MainActivity.class.getSimpleName();
    private static String FRAGMENT_TAG = "screen";
    private TranslationContext translationContext = new TranslationContext();

    private BottomNavigationView navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, String.format("OnCreate bundle: %s", savedInstanceState));
        setContentView(R.layout.activity_main);
        navigation = initNavigation();
        onRestoreInstanceState(savedInstanceState);
        requestInternet(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (translationContext.getConnector() != null) {
            translationContext.getConnector().unSubscribe();
            translationContext.getConnector().dropConnection();
            translationContext.setConnector(null);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.d(TAG, "onSaveInstanceState: ");
        super.onSaveInstanceState(outState);
        if (translationContext != null) {
            outState.putSerializable(TranslationContext.BUNDLE_TRANSLATION_CONTEXT, translationContext);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.d(TAG, String.format("onRestoreInstanceState bundle: %s", savedInstanceState));
        if (savedInstanceState != null) {
            super.onRestoreInstanceState(savedInstanceState);
            translationContext = ((TranslationContext) savedInstanceState
                    .get(TranslationContext.BUNDLE_TRANSLATION_CONTEXT));
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

    @NonNull
    private TranslatorScreenFragment initTranslatorScreenFragment() {
        TranslatorScreenFragment translator = new TranslatorScreenFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(TranslationContext.BUNDLE_TRANSLATION_CONTEXT, translationContext);
        translator.setArguments(bundle);
        return translator;
    }

    private void initFirstTime(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            navigation.setSelectedItemId(R.id.action_translate_screen);
            translationContext = initTranslationContext();
        }
    }

    private TranslationContext initTranslationContext() {
        TranslationContext translationContext = new TranslationContext();
        this.translationContext.setSource(Language.EN)
                .setTarget(Language.RU)
                .setUi(Language.RU);
        this.translationContext.setConnector(initConnector(getExternalCacheDir(),
                this.translationContext.getUi()));
        return translationContext;
    }

    private static NetworkUIConnector initConnector(File cache, Language uiLanguage) {
        ConnectionBuilder connectionBuilder = new ConnectionBuilder(cache);
        YandexTranslateRepository translateRepo =
                new YandexTranslateRepository(connectionBuilder);
        YandexDictionaryRepository dictionaryRepo =
                new YandexDictionaryRepository(connectionBuilder, uiLanguage);
        YandexTranslator net = new YandexTranslator(translateRepo, dictionaryRepo);
        return new NetworkUIConnector(net);
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


}
