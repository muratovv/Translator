package yandex.muratov.translator.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;

import yandex.muratov.translator.storage.AbstractHistoryStorage;
import yandex.muratov.translator.storage.DbHistoryStorage;
import yandex.muratov.translator.storage.StorageController;
import yandex.muratov.translator.translate.TranslationController;
import yandex.muratov.translator.translate.data.Language;
import yandex.muratov.translator.translate.net.ConnectionBuilder;
import yandex.muratov.translator.translate.net.YandexDictionaryRepository;
import yandex.muratov.translator.translate.net.YandexTranslateRepository;
import yandex.muratov.translator.translate.net.YandexTranslator;

public class ContextHolderFragment extends Fragment {

    public static String TAG = ContextHolderFragment.class.getSimpleName();

    private TranslationContext translationContext;
    private HistoryStorageContext historyContext;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        initContext(getActivity());
    }

    private void initContext(Activity activity) {
        translationContext = initTranslationContext(activity.getExternalCacheDir(), Language.RU);
        historyContext = initHistoryContext();
    }

    private HistoryStorageContext initHistoryContext() {
        AbstractHistoryStorage storage = new DbHistoryStorage(getContext());
        return new HistoryStorageContext(new StorageController(storage));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return null;
    }

    private static TranslationContext initTranslationContext(File cache, Language uiLanguage) {
        TranslationController networkConnector = initConnector(cache, Language.EN, Language.RU, uiLanguage);
        return new TranslationContext(networkConnector);
    }

    private static TranslationController initConnector(File cache,
                                                       Language sourceLang,
                                                       Language targetLang,
                                                       Language uiLanguage) {
        ConnectionBuilder connectionBuilder = new ConnectionBuilder(cache);
        YandexTranslateRepository translateRepo =
                new YandexTranslateRepository(connectionBuilder);
        YandexDictionaryRepository dictionaryRepo =
                new YandexDictionaryRepository(connectionBuilder, uiLanguage);
        YandexTranslator net = new YandexTranslator(translateRepo, dictionaryRepo);
        return new TranslationController(net, sourceLang, targetLang);
    }

    public TranslationContext getTranslationContext() {
        return translationContext;
    }

    public HistoryStorageContext getHistoryContext() {
        return historyContext;
    }

    public static ContextHolderFragment findContextFragment(Fragment current) {
        return ((ContextHolderFragment) current.getFragmentManager().findFragmentByTag(ContextHolderFragment.TAG));
    }
}
