package com.unlam.developerstudentclub.silapu.API;

import com.unlam.developerstudentclub.silapu.BuildConfig;
import com.unlam.developerstudentclub.silapu.Entity.UserData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("user/login")
    Call<ApiResponse<UserData>> getLogin(@Query("key") String key, @Query("email") String email, @Query("password") String password);

    @POST("user/register")
    Call<ApiResponse<List<UserData>>> postRegister(@Body UserData data);

//    @GET("prodi/"+ BuildConfig.API_KEY + "{id}")
//    Call<ApiResponse<Prodi>> getOneProdi(@Path("id") int id);
//
//    @POST("prodi")
//    Call<ApiResponse<JsonObject>> tambahProdi(@Body Prodi prodi);
//
//    @PUT("prodi/{id}")
//    Call<ApiResponse<JsonObject>> ubahProdi(@Path("id") int id,@Body Prodi prodi);
//
//    @DELETE("prodi/{id}")
//    Call<ApiResponse<JsonObject>> deleteProdi(@Path("id") int id);
}
