package com.nuhel.stripetry.Stripe.Retrofit;


import android.util.Log;

import com.nuhel.stripetry.Stripe.Retrofit.Endpoints;

import retrofit2.Retrofit;

import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class StripeRetrofitFactory {
    private static Retrofit retrofit = null;

    public static Retrofit getEndpointsFactory(String baseURL) {
        if (retrofit == null) {
            try {
                retrofit = new Retrofit.Builder()
                        .baseUrl(baseURL)
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .build();
            } catch (Exception e) {
                Log.d("TAG", e.getMessage());
            }
        }
        return retrofit;
    }

    public static Endpoints getEndpoints() {
        return getEndpointsFactory("https://devsite.airportshuttles.com/stripe/").create(Endpoints.class);
    }
}
