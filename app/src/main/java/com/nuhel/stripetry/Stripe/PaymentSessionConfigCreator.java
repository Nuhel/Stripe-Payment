package com.nuhel.stripetry.Stripe;

import android.util.Log;

import androidx.annotation.NonNull;

import com.nuhel.stripetry.Stripe.Retrofit.StripeEndpoints;
import com.nuhel.stripetry.Stripe.Retrofit.StripeRetrofitFactory;
import com.nuhel.stripetry.Stripe.callbacks.PaymentSessionCallback;
import com.stripe.android.PaymentSession;
import com.stripe.android.PaymentSessionConfig;
import com.stripe.android.PaymentSessionData;
import com.stripe.android.model.PaymentMethod;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;

public class PaymentSessionConfigCreator {
    @NonNull
    public static PaymentSessionConfig getPaymentSessionConfig() {
        return new PaymentSessionConfig.Builder()
                .setPaymentMethodTypes(
                        Arrays.asList(PaymentMethod.Type.Card)
                )
                .setShouldShowGooglePay(false)
                .setShippingMethodsRequired(false)
                .setShippingInfoRequired(false)
                .build();
    }


    @NonNull
    public static PaymentSession.PaymentSessionListener getPaymentSessionListener(final PaymentSessionCallback paymentSessionCallback, final String baseUrl,final String secretKeyUrl,final int amount) {
        return new PaymentSession.PaymentSessionListener() {

            PaymentMethod paymentMethod;

            @Override
            public void onCommunicatingStateChanged(boolean isCommunicating) {
                paymentSessionCallback.onCommunicationChanged(isCommunicating);
            }

            @Override
            public void onError(int errorCode, @NotNull String errorMessage) {
                paymentSessionCallback.onPaymentSessionError(errorCode,errorMessage);
            }


            @Override
            public void onPaymentSessionDataChanged(@NonNull PaymentSessionData data) {
                //Log.e("data", data.toString());
                if (data.getUseGooglePay()) {
                    // customer intends to pay with Google Pay
                    //Log.e("using", "play");
                }

                // Update your UI here with other data
                if (data.isPaymentReadyToCharge()) {

                    paymentMethod = data.getPaymentMethod();
                    // Use the data to complete your charge - see below.
                    if (paymentMethod != null) {

                        StripeEndpoints stripeEndpoints = StripeRetrofitFactory.getClientSecretEndPoint(baseUrl);;
                        CompositeDisposable compositeDisposable = new CompositeDisposable();

                        compositeDisposable.add(stripeEndpoints.getClientSecret(secretKeyUrl,paymentMethod.id, String.valueOf(amount))
                                .subscribeOn(Schedulers.io())
                                .onErrorResumeNext(new Single<ClientSecretModel>() {
                                    @Override
                                    protected void subscribeActual(@io.reactivex.annotations.NonNull SingleObserver<? super ClientSecretModel> observer) {
                                        Log.e("KeyProvider", "Error : " + observer);
                                    }
                                })
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(
                                        response -> {
                                            try {
                                                paymentSessionCallback.onSecretKeyGenerated(paymentMethod.id, response.getClientSecret());
                                                //Log.e("response", "response : " + response.getClientSecret());
                                                //keyUpdateListener.onKeyUpdate(response);
                                            } catch (Exception e) {

                                            }
                                        }));

                        /*AndroidNetworking.get("https://devsite.airportshuttles.com/stripe/makePayment.php")
                                .addQueryParameter("id", paymentMethod.id)
                                .addQueryParameter("amount", amount+"")
                                .setPriority(Priority.HIGH)
                                .build()
                                .getAsJSONObject(new JSONObjectRequestListener() {

                                    @Override
                                    public void onResponse(JSONObject response) {
                                        try {
                                            paymentSessionCallback.onSecretKeyGenerated(paymentMethod.id, response.getString("clientSecret"));
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    @Override
                                    public void onError(ANError anError) {

                                    }
                                });*/
                        //
                    } else {
                        Log.e("log-payment_method", "Null");
                    }

                }
            }


        };
    }

}
