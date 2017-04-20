package yandex.muratov.translator.network;

import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import yandex.muratov.translator.BuildConfig;
import yandex.muratov.translator.network.data.DictionaryAnswer;
import yandex.muratov.translator.network.data.TranslateAnswer;
import yandex.muratov.translator.network.api.TranslatorModelSubscriber;
import yandex.muratov.translator.network.api.NetworkTranslatorModel;
import yandex.muratov.translator.network.api.TranslateAPI;


public class YandexTranslator implements NetworkTranslatorModel {

    private static NetworkTranslatorModel model;

    private final String TRANSLATE_SERVICE_URL = "https://translate.yandex.net";
    private final String translateKey = BuildConfig.YANDEX_TRANSLATOR_API_TOKEN;
    private final String dictionaryKey = BuildConfig.YANDEX_DICTIONARY_API_TOKEN;


    private OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .build();
    private Retrofit retrofit = new Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(TRANSLATE_SERVICE_URL)
            .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().create()))
            .build();
    private TranslateAPI service = retrofit.create(TranslateAPI.class);

    private TranslatorModelSubscriber subscriber;
    private CallHolder<DictionaryAnswer> dictionaryCall = new CallHolder<>();
    private CallHolder<TranslateAnswer> translateCall = new CallHolder<>();


    private YandexTranslator() {
    }

    public static NetworkTranslatorModel getInstance() {
        if (model == null)
            model = new YandexTranslator();
        return model;
    }

    @Override
    public void subscribe(TranslatorModelSubscriber subscriber) {
        this.subscriber = subscriber;
    }

    @Override
    public void unSubscribe() {
        subscriber = null;
        denyLastRequest();
    }

    @Override
    public void denyLastRequest() {
        dictionaryCall.dropPreviousCall();
        translateCall.dropPreviousCall();
    }

    @Override
    public void dropConnection() {
        denyLastRequest();
        okHttpClient.dispatcher().executorService().shutdown();
    }

    @Override
    public void translateRequest(String rawLang, String text) {
        Call<TranslateAnswer> call = service.translate(translateKey, rawLang, text);
        translateCall.enqueue(call, new Callback<TranslateAnswer>() {
            @Override
            public void onResponse(Call<TranslateAnswer> call, Response<TranslateAnswer> response) {
                if (subscriber != null) {
                    subscriber.onTranslateResponse(response.body());
                }
            }

            @Override
            public void onFailure(Call<TranslateAnswer> call, Throwable t) {
                if (subscriber != null) {
                    subscriber.onTranslateRequestFail(t);
                }
            }
        });
    }

    @Override
    public void dictionaryRequest(String rawLang, String uiLang, String text) {
        Call<DictionaryAnswer> call = service.dictionary(dictionaryKey, rawLang, uiLang, text);
        dictionaryCall.enqueue(call, new Callback<DictionaryAnswer>() {
            @Override
            public void onResponse(Call<DictionaryAnswer> call, Response<DictionaryAnswer> response) {
                if (subscriber != null) {
                    subscriber.onDictionaryResponse(response.body());
                }
            }

            @Override
            public void onFailure(Call<DictionaryAnswer> call, Throwable t) {
                if (subscriber != null) {
                    subscriber.onDictionaryRequestFail(t);
                }
            }
        });
    }


}
