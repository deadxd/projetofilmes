package com.example.monster.projetofilmeseries.api;

import com.example.monster.projetofilmeseries.model.Filme;
import com.example.monster.projetofilmeseries.model.Results;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Service {

    /* https://api.themoviedb.org/3/movie/popular?api_key=aaf5f3fae661b01b6b338b439b4e3c70&language=pt-BR */

    @GET("movie/popular")
    Call<Filme> recuperarPopular(@Query("api_key") String api_key,
                                 @Query("language") String language);

    @GET("movie/top_rated")
    Call<Filme> recuperarRated(@Query("api_key") String api_key,
                                  @Query("language") String language);

    @GET("movie/upcoming")
    Call<Filme> recuperarLancamento(@Query("api_key") String api_key,
                                    @Query("language") String language);

    /*@GET("movie/{movie_id}")
    Call<Results> recuperarFavoritos(@Path("movie_id") int id, @Query("api_key") String apiKey);*/

}
