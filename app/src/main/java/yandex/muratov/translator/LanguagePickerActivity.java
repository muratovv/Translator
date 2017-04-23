package yandex.muratov.translator;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import yandex.muratov.translator.network.data.Language;
import yandex.muratov.translator.ui.translator.LanguagePickerToolbar;
import yandex.muratov.translator.ui.translator.OnChangeLanguage;
import yandex.muratov.translator.ui.translator.TranslatorScreenFragment;


public class LanguagePickerActivity extends AppCompatActivity {

    public static final String TITLE_TAG = "title_tag";
    public static final String CALLBACK_TAG = "callback_tag";

    public static final String SOURCE_LANG_IDENTIFIER = "source_lang_tag";
    public static final String TARGET_LANG_IDENTIFIER = "target_lang_tag";

    private OnChangeLanguage changeLanguageNotification;

    private LanguagePickerToolbar toolbar;
    private ListView listOfLanguages;
    private String title;

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

        List<String> languages = availableNameLanguages();
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(this, R.layout.item_language,
                        R.id.item_language_text, languages);
        listOfLanguages.setAdapter(adapter);
        listOfLanguages.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Language lang = Language.availableLanguages.get(position);
                if (changeLanguageNotification != null) {
                    changeLanguageNotification.notify(lang);
                }
                finish();
            }
        });
    }

    private ListView initListOfLanguages() {
        return ((ListView) findViewById(R.id.list_of_languages));
    }

    private LanguagePickerToolbar initToolbar() {
        LanguagePickerToolbar toolbar = (LanguagePickerToolbar) findViewById(R.id.toolbar_language_picker);
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

    private List<String> availableNameLanguages() {
        List<String> languages = new ArrayList<>();
        for (Language language : Language.availableLanguages) {
            languages.add(language.getUiName());
        }
        return languages;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
