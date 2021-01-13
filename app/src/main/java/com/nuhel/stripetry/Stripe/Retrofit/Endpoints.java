package com.nuhel.stripetry.Stripe.Retrofit;


import io.reactivex.Single;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Endpoints {
    @FormUrlEncoded
    @POST("createEphemeralKey.php")
    Single<String> createEphemeralKey(@Field("apiVersion") String apiVersion,@Field("customerId") String customerId);
}


