package com.unlam.developerstudentclub.silapu.API;

import com.unlam.developerstudentclub.silapu.Entity.PengaduanItem;
import com.unlam.developerstudentclub.silapu.Entity.PerdataItem;
import com.unlam.developerstudentclub.silapu.Entity.UserData;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

public interface ApiInterface {

    /*User API*/

    @GET("user/login")
    Call<ApiResponseUser<UserData>> getLogin(
            @Query("key") String key,
            @Query("email") String email,
            @Query("password") String password
    );

    @Multipart
    @POST("user/register")
    Call<ApiResponseUser<UserData>> postRegister(
            @PartMap Map<String, RequestBody> partMap,
            @Part MultipartBody.Part file
    );

    /*Data API*/

    @GET("http://silapu.ulm.ac.id/service/api/permintaan/data")
    Call<ApiResponseData<PerdataItem>> getPerdata(
            @Query("key") String key,
            @Query("id") int id
    );

    @GET("http://silapu.ulm.ac.id/service/api/pengaduan/data")
    Call<ApiResponseData<PengaduanItem>> getPengaduan(
            @Query("key") String key,
            @Query("id") int id
    );


//    @GET("prodi/"+ BuildConfig.API_KEY + "{id}")
//    Call<ApiResponseUser<Prodi>> getOneProdi(@Path("id") int id);
//
//    @POST("prodi")
//    Call<ApiResponseUser<JsonObject>> tambahProdi(@Body Prodi prodi);
//
//    @PUT("prodi/{id}")
//    Call<ApiResponseUser<JsonObject>> ubahProdi(@Path("id") int id,@Body Prodi prodi);
//
//    @DELETE("prodi/{id}")
//    Call<ApiResponseUser<JsonObject>> deleteProdi(@Path("id") int id);
}
