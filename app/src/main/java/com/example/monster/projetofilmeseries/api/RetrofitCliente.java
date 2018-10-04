package com.example.monster.projetofilmeseries.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitCliente {

    public static final String BASE_URL = "http://api.themoviedb.org/3/";
    public static final String API_KEY = "aaf5f3fae661b01b6b338b439b4e3c70";

    public static Retrofit getRetrofit(){
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }



}
