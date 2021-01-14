package com.nuhel.stripetry.Stripe.Retrofit;

import io.reactivex.Single;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface StripeEndpoints {
    @FormUrlEncoded
    @POST()
    Single<String> getEphemeralKey(@Url String url, @Field("apiVersion") String apiVersion, @Field("customerId") String customerId);




}


