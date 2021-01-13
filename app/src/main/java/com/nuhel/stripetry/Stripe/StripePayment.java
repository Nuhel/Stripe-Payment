package com.nuhel.stripetry.Stripe;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.nuhel.stripetry.Stripe.callbacks.PaymentResultCallback;
import com.nuhel.stripetry.Stripe.callbacks.PaymentSessionCallback;
import com.stripe.android.CustomerSession;
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.PaymentSession;
import com.stripe.android.Stripe;
import com.stripe.android.model.ConfirmPaymentIntentParams;
import com.stripe.android.view.PaymentMethodsActivityStarter;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.concurrent.Callable;


public class StripePayment implements PaymentSessionCallback {

    //@params Static Params for basic initialization
    private static Context appContext;
    private static String customerId;
    private static boolean isInitialized = false;
    private static String publishableKey = "pk_test_51HYDPKKKrgZqoLaaBx4sFoLpni1rxR7rNKjzbom6Ur08muGttmXNvMFYmi4uvCcL4lPNkYJPuNtqQ3uKE2dIoVP500W1PI1sse";




    public static void initStripe(Context context,String customerId) throws Exception {
        if(isInitialized != false){
            Log.d("Stripe", "Already Initialized");
        }else{
            StripePayment.appContext  = context;
            StripePayment.customerId  = customerId;
            if(context != null && context instanceof Application){
                PaymentConfiguration.init(
                        context,
                        StripePayment.publishableKey
                );
                initCustomerSession(StripePayment.customerId);
                isInitialized = true;
            }else{
                throw new Exception("Context is missing");
            }
        }
    }

    private static void initCustomerSession(String customerId){
        CustomerSession.initCustomerSession(StripePayment.appContext, new EphemeralKeyBuilder(customerId));
    }


    private static Context getContext(){
        return  StripePayment.appContext;
    }

    public static boolean isIsInitialized(){
        return StripePayment.isInitialized;
    }


    //@params NonStatic Params to make payment
    @NonNull private AppCompatActivity activity;
    private PaymentSession paymentSession;
    private Stripe stripe;
    private Callable<Void> callExecutePaymentResult;


    public StripePayment(AppCompatActivity activity){
        this.activity = activity;
        this.stripe =  new Stripe(activity, Objects.requireNonNull(StripePayment.publishableKey));


    }

    public void initPaymentSession(int amount, String desc, int requestCode, int resultCode, @Nullable Intent data){
        paymentSession = new PaymentSession(
                activity,
                PaymentSessionConfigCreator.getPaymentSessionConfig()
        );

        paymentSession.init(PaymentSessionConfigCreator.getPaymentSessionListener(this));
        PaymentMethodsActivityStarter.Result result = PaymentMethodsActivityStarter.Result.fromIntent(data);
        paymentSession.handlePaymentData(requestCode, resultCode, data);
    }

    public void executePaymentResult(int requestCode, Intent data){
        stripe.onPaymentResult(requestCode, data, new PaymentResultCallback(activity));
    }

    @Override
    public void onCommunicationChanged(boolean isCommunicating) {

    }

    @Override
    public void onSecretKeyGenerated(String paymentMethodId, String clientSecret) {
        ConfirmPaymentIntentParams confirmPaymentIntentParams =  ConfirmPaymentIntentParams.createWithPaymentMethodId(paymentMethodId, clientSecret, "");
        stripe.confirmPayment(activity,confirmPaymentIntentParams);
    }

    @Override
    public void onError(int errorCode, @NotNull String errorMessage) {

    }


}
