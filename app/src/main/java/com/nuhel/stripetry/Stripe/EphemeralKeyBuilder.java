package com.nuhel.stripetry.Stripe;


import androidx.annotation.Size;

import com.nuhel.stripetry.Stripe.Retrofit.Endpoints;
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

    private final Endpoints endpoints = StripeRetrofitFactory.getEndpoints();

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    private String customerId;

    public EphemeralKeyBuilder(String customerId){
        this.customerId = customerId;
    }

    @Override
    public void createEphemeralKey(
            @NonNull @Size(min = 4) String apiVersion,
            @NonNull final EphemeralKeyUpdateListener keyUpdateListener) {

        compositeDisposable.add(endpoints.createEphemeralKey(apiVersion,customerId)
                .subscribeOn(Schedulers.io())
                .onErrorResumeNext(new Single<String>() {
                    @Override
                    protected void subscribeActual(@NonNull SingleObserver<? super String> observer) {
                        //Log.e("KeyProvider", "Error : " + observer);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response -> {
                            try {
                                keyUpdateListener.onKeyUpdate(response);
                            } catch (Exception e) {
                                //Log.e("KeyProvider", "Error : " + e.getMessage());
                            }
                        }));
    }


}

