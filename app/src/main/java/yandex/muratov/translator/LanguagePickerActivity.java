package yandex.muratov.translator;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import yandex.muratov.translator.network.data.Language;


public class LanguagePickerActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ListView listOfLanguages;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_language_picker);
        initViews();
        super.onCreate(savedInstanceState);
    }

    private void initViews() {
        toolbar = initToolbar();
        listOfLanguages = initListOfLanguages();

        List<String> languages = availableNameLanguages();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.item_language, R.id.item_language_text, languages);
        listOfLanguages.setAdapter(adapter);
        listOfLanguages.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String code = Language.availableLanguages.get(position).getCode();
                Toast.makeText(LanguagePickerActivity.this, code, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private ListView initListOfLanguages() {
        return ((ListView) findViewById(R.id.list_of_languages));
    }

    private Toolbar initToolbar() {
        return ((Toolbar) findViewById(R.id.toolbar_language_picker));
    }

    private List<String> availableNameLanguages(){
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
