package com.nuhel.stripetry.Stripe;


import android.util.Log;

import androidx.annotation.Size;

import com.nuhel.stripetry.Stripe.Retrofit.StripeEndpoints;
import com.nuhel.stripetry.Stripe.Retrofit.StripeRetrofitFactory;
import com.stripe.android.EphemeralKeyProvider;
import com.stripe.android.EphemeralKeyUpdateListener;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class EphemeralKeyBuilder implements EphemeralKeyProvider {

    private CompositeDisposable compositeDisposable ;

    private String customerId;
    private String ephemeralKeyProviderUrl;
    private StripeEndpoints stripeEndpoints;;

    public EphemeralKeyBuilder(String customerId, String baseUrl,String ephemeralKeyProviderUrl) {
        this.customerId = customerId;
        this.stripeEndpoints = StripeRetrofitFactory.getEphemeralKeyEndpoint(baseUrl);;
        this.ephemeralKeyProviderUrl = ephemeralKeyProviderUrl;
        this.compositeDisposable = new CompositeDisposable();;
    }

    @Override
    public void createEphemeralKey(@NonNull @Size(min = 4) String apiVersion, @NonNull final EphemeralKeyUpdateListener keyUpdateListener) {

        compositeDisposable.add(stripeEndpoints.getEphemeralKey(ephemeralKeyProviderUrl,apiVersion, customerId)
                .subscribeOn(Schedulers.io())
                .onErrorResumeNext(new Single<String>() {
                    @Override
                    protected void subscribeActual(@NonNull SingleObserver<? super String> observer) {
                        Log.e("KeyProvider", "Error : " + observer);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response -> {
                            try {
                                //Log.e("KeyProvider", "Error : " + response);
                                keyUpdateListener.onKeyUpdate(response);
                            } catch (Exception e) {

                            }
                        }));
    }


}

