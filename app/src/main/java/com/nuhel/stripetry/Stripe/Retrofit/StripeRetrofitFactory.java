package com.nuhel.stripetry.Stripe.Retrofit;


import android.util.Log;

import retrofit2.Retrofit;

import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class StripeRetrofitFactory {

    public static Retrofit getEphemeralKeyEndpointFactory(String baseURL) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        return retrofit;
    }

    public static StripeEndpoints getEphemeralKeyEndpoint(String baseUrl) {
        return getEphemeralKeyEndpointFactory(baseUrl).create(StripeEndpoints.class);
    }


    public static Retrofit getClientSecretRetrofitFactory(String baseURL) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }

    public static  StripeEndpoints getClientSecretEndPoint(String baseUrl) {
        return getClientSecretRetrofitFactory(baseUrl).create(StripeEndpoints.class);
    }
}
