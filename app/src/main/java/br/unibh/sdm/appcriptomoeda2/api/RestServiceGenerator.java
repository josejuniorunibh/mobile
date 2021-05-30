package br.unibh.sdm.appcriptomoeda2.api;

import android.util.Log;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestServiceGenerator {

    public static final String API_BASE_URL = "http://10.0.2.2:8080/cripto-api/";

    public static <S> S createService(Class<S> serviceClass) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        Log.i("RestServiceGenerator","Criada a conexao com a api rest");
        return retrofit.create(serviceClass);
    }

}
