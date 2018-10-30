package com.unlam.developerstudentclub.silapu.API;

import com.unlam.developerstudentclub.silapu.BuildConfig;
import com.unlam.developerstudentclub.silapu.Entity.UserData;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("user/login")
    Call<ApiResponse<UserData>> getLogin(@Query("key") String key, @Query("email") String email, @Query("password") String password);

    @Multipart
    @POST("user/register")
    Call<ApiResponse<UserData>> postRegister(
            @PartMap Map<String, RequestBody> partMap,
            @Part MultipartBody.Part file);


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
