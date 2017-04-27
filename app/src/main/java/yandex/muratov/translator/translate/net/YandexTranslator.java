package yandex.muratov.translator.translate.net;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import yandex.muratov.translator.translate.CallHolder;
import yandex.muratov.translator.translate.api.DictionaryRepository;
import yandex.muratov.translator.translate.api.TranslationModel;
import yandex.muratov.translator.translate.api.TranslateRepository;
import yandex.muratov.translator.translate.api.TranslatorModelSubscriber;
import yandex.muratov.translator.translate.data.DictionaryAnswer;
import yandex.muratov.translator.translate.data.TranslateAnswer;


public class YandexTranslator implements TranslationModel {

    private final TranslateRepository translateRepository;
    private final DictionaryRepository dictionaryRepository;

    private TranslatorModelSubscriber subscriber;
    private CallHolder<DictionaryAnswer> dictionaryCall = new CallHolder<>();
    private CallHolder<TranslateAnswer> translateCall = new CallHolder<>();


    public YandexTranslator(TranslateRepository translateRepository, DictionaryRepository dictionaryRepository) {
        this.translateRepository = translateRepository;
        this.dictionaryRepository = dictionaryRepository;
    }

    @Override
    public void subscribe(TranslatorModelSubscriber subscriber) {
        this.subscriber = subscriber;
    }

    @Override
    public void unsubscribe() {
        subscriber = null;
        dropLast();
    }

    @Override
    public void dropLast() {
        dictionaryCall.dropPreviousCall();
        translateCall.dropPreviousCall();
    }

    @Override
    public void dropConnection() {
        dropLast();
        dictionaryRepository.closeConnection();
        translateRepository.closeConnection();
    }

    @Override
    public void translateRequest(String rawLang, String text) {
        Call<TranslateAnswer> call = translateRepository.translate(rawLang, text);
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
                    if (!isCancelledException(t)) {
                        subscriber.onTranslateRequestFail(t);
                    }
                }
            }
        });
    }

    @Override
    public void dictionaryRequest(String rawLang, String text) {
        Call<DictionaryAnswer> call = dictionaryRepository.dictionary(rawLang, text);
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
                    if (!isCancelledException(t)) {
                        subscriber.onDictionaryRequestFail(t);
                    }
                }
            }
        });
    }

    private static boolean isCancelledException(Throwable er) {
        return er instanceof IOException;
    }

}
