package com.nuhel.stripetry.Stripe;

import android.content.Intent;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.nuhel.stripetry.Stripe.callbacks.PaymentResultCallback;
import com.nuhel.stripetry.Stripe.callbacks.PaymentResultCallbackListener;
import com.nuhel.stripetry.Stripe.callbacks.PaymentSessionCallback;
import com.stripe.android.PaymentSession;
import com.stripe.android.Stripe;
import com.stripe.android.model.ConfirmPaymentIntentParams;
import org.jetbrains.annotations.NotNull;
import java.util.Objects;


public class StripePayment implements PaymentSessionCallback {

    public static final int PAYMENT_CONFIRM_REQUEST_CODE = 50000;

    //@params NonStatic Params to make payment
    private AppCompatActivity activity;
    private PaymentSession paymentSession;
    private Stripe stripe;
    private PaymentResultCallback paymentResultCallback;


    public StripePayment(AppCompatActivity activity, PaymentResultCallback paymentResultCallback, String publishableKey) {
        this.activity = activity;
        this.paymentResultCallback = paymentResultCallback;
        this.stripe = new Stripe(activity, Objects.requireNonNull(publishableKey));
    }

    public void initPaymentSession(int amount, String desc, int requestCode, int resultCode, @Nullable Intent data) {
        paymentSession = new PaymentSession(
                activity,
                PaymentSessionConfigCreator.getPaymentSessionConfig()
        );

        paymentSession.init(PaymentSessionConfigCreator.getPaymentSessionListener(this, amount));
        paymentSession.handlePaymentData(requestCode, resultCode, data);
    }

    public void executePaymentResult(int requestCode, Intent data) {
        stripe.onPaymentResult(requestCode, data, new PaymentResultCallbackListener(paymentResultCallback));
    }

    @Override
    public void onCommunicationChanged(boolean isCommunicating) {
        if(isCommunicating){
            paymentResultCallback.onPaymentFlowStarted();
        }else{
            paymentResultCallback.onPaymentFlowStopped();
        }
    }

    @Override
    public void onSecretKeyGenerated(String paymentMethodId, String clientSecret) {
        ConfirmPaymentIntentParams confirmPaymentIntentParams = ConfirmPaymentIntentParams.createWithPaymentMethodId(paymentMethodId, clientSecret, "");
        stripe.confirmPayment(activity, confirmPaymentIntentParams);
    }

    @Override
    public void onPaymentSessionError(int errorCode, @NotNull String errorMessage) {
        paymentResultCallback.onPaymentSessionError(errorMessage);
    }

    public static StripePaymentBuilder.StripePaymentInstanceBuilder builder() {
        return new StripePaymentBuilder.StripePaymentInstanceBuilder();
    }

}
