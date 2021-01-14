package com.nuhel.stripetry.Stripe.callbacks;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.stripe.android.ApiResultCallback;
import com.stripe.android.PaymentIntentResult;
import com.stripe.android.model.PaymentIntent;

import java.util.Objects;

public class PaymentResultCallbackListener implements ApiResultCallback<PaymentIntentResult> {

    private PaymentResultCallback paymentResultCallback;

    public PaymentResultCallbackListener(PaymentResultCallback paymentResultCallback) {
        this.paymentResultCallback = paymentResultCallback;
    }

    @Override

    public void onSuccess(@NonNull PaymentIntentResult result) {
        PaymentIntent paymentIntent = result.getIntent();
        PaymentIntent.Status status = paymentIntent.getStatus();

        if (status == PaymentIntent.Status.Succeeded) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            paymentResultCallback.onPaymentSucceeded(gson.toJson(paymentIntent));
        } else if (status == PaymentIntent.Status.RequiresPaymentMethod) {
            paymentResultCallback.onRequiresPaymentMethod(Objects.requireNonNull(paymentIntent.getLastPaymentError()).getMessage());
        }

    }

    @Override

    public void onError(@NonNull Exception e) {
        paymentResultCallback.onPaymentFailed(e.getMessage());
    }

}
