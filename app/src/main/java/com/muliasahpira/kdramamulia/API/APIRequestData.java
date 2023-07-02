package com.muliasahpira.kdramamulia.API;

import com.muliasahpira.kdramamulia.Model.ModelResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIRequestData {
    @FormUrlEncoded
    @POST("loginkdrama.php")
    Call<ModelResponse> ardLogin(
            @Field("username") String username,
            @Field("password") String password
    );

    @GET("retrievekdrama.php")
    Call<ModelResponse> ardRetrieve();

    @FormUrlEncoded
    @POST("createkdrama.php")
    Call<ModelResponse> ardCreate(
            @Field("gambar") String gambar,
            @Field("judul") String judul,
            @Field("hangul") String hangul,
            @Field("sinopsis") String sinopsis,
            @Field("rilis") String rilis,
            @Field("genre") String genre,
            @Field("rating") String rating,
            @Field("episode") String episode,
            @Field("pemeran") String pemeran
    );

    @FormUrlEncoded
    @POST("updatekdrama.php")
    Call<ModelResponse> ardUpdate(
            @Field("id") String id,
            @Field("gambar") String gambar,
            @Field("judul") String judul,
            @Field("hangul") String hangul,
            @Field("sinopsis") String sinopsis,
            @Field("rilis") String rilis,
            @Field("genre") String genre,
            @Field("rating") String rating,
            @Field("episode") String episode,
            @Field("pemeran") String pemeran
    );

    @FormUrlEncoded
    @POST("deletekdrama.php")
    Call<ModelResponse> ardDelete(
            @Field("id") String id
    );

    @FormUrlEncoded
    @POST("showkdrama.php")
    Call<ModelResponse> ardShow(
            @Field("id") String id
    );
}
