package yandex.muratov.translator.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import yandex.muratov.translator.R;
import yandex.muratov.translator.translate.data.Language;
import yandex.muratov.translator.ui.translator.LanguagePickerToolbarView;
import yandex.muratov.translator.ui.translator.OnChangeLanguage;
import yandex.muratov.translator.ui.translator.TranslatorScreenFragment;
import yandex.muratov.translator.ui.translator.language_picker.LanguagePickerAdapter;
import yandex.muratov.translator.util.AndroidUtil;


public class LanguagePickerActivity extends AppCompatActivity {

    public static final String TITLE_TAG = "title_tag";
    public static final String CALLBACK_TAG = "callback_tag";

    public static final String SOURCE_LANG_IDENTIFIER = "source_lang_tag";
    public static final String TARGET_LANG_IDENTIFIER = "target_lang_tag";

    public static final int LIST_CACHE_SIZE = 10;

    private OnChangeLanguage changeLanguageNotification;

    private LanguagePickerToolbarView toolbar;
    private RecyclerView listOfLanguages;
    private String title;
    private AndroidUtil.OnRecyclerViewItemClickListener itemListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language_picker);
        initModel();
        initViews();
    }

    private void initModel() {
        Bundle bundle = getIntent().getExtras();
        changeLanguageNotification = getCallback(bundle.getString(CALLBACK_TAG));
        itemListener = new AndroidUtil.OnRecyclerViewItemClickListener() {
            @Override
            public void onClick(Language language) {
                if (changeLanguageNotification != null) {
                    changeLanguageNotification.notify(language);
                }
                finish();
            }
        };
        title = bundle.getString(TITLE_TAG);
    }

    private OnChangeLanguage getCallback(String identifier) {
        switch (identifier) {
            case LanguagePickerActivity.SOURCE_LANG_IDENTIFIER:
                return TranslatorScreenFragment.sourceLanguageChange;
            case LanguagePickerActivity.TARGET_LANG_IDENTIFIER:
                return TranslatorScreenFragment.targetLanguageChange;
        }
        return null;
    }

    private void initViews() {
        toolbar = initToolbar();
        listOfLanguages = initListOfLanguages();
        fillListOfLanguages();

    }

    private RecyclerView initListOfLanguages() {
        return ((RecyclerView) findViewById(R.id.list_of_languages));
    }

    private void fillListOfLanguages() {
        listOfLanguages.setHasFixedSize(true);
        listOfLanguages.setItemViewCacheSize(LIST_CACHE_SIZE);
        listOfLanguages.setLayoutManager(new LinearLayoutManager(this));
        LanguagePickerAdapter adapter = new LanguagePickerAdapter(Language.availableLanguages,
                itemListener);
        listOfLanguages.setAdapter(adapter);
    }

    private LanguagePickerToolbarView initToolbar() {
        LanguagePickerToolbarView toolbar = (LanguagePickerToolbarView) findViewById(R.id.toolbar_language_picker);
        toolbar.getBackButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        if (title != null) {
            toolbar.getTitleView().setText(title);
        }
        return toolbar;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
