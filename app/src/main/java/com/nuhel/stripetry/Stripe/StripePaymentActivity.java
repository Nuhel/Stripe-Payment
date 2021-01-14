package com.nuhel.stripetry.Stripe;

import android.content.Intent;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.nuhel.stripetry.Stripe.callbacks.PaymentResultCallback;
import com.stripe.android.view.AddPaymentMethodActivityStarter;
import com.stripe.android.view.PaymentMethodsActivityStarter;

public class StripePaymentActivity extends AppCompatActivity implements PaymentResultCallback {
    private StripePayment stripePayment;
    private int amount;
    private StripePaymentBuilder stripePaymentBuilder;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PaymentMethodsActivityStarter.REQUEST_CODE) {
            stripePayment = new StripePayment(this, this,stripePaymentBuilder.getPublishableKey());
            stripePayment.initPaymentSession(amount,"",requestCode,resultCode,data);
        } else if (requestCode == AddPaymentMethodActivityStarter.REQUEST_CODE) {
             AddPaymentMethodActivityStarter.Result result = AddPaymentMethodActivityStarter.Result.fromIntent(data);
        } else if(StripePayment.PAYMENT_CONFIRM_REQUEST_CODE == requestCode ) {
            if(stripePayment != null){
                stripePayment.executePaymentResult(requestCode, data);
                stripePayment = null;
            }
        }

    }

    public void setAmount(int amount){
        this.amount = amount;
    }

    public void setStripePaymentBuilder(StripePaymentBuilder stripePaymentBuilder){
        this.stripePaymentBuilder = stripePaymentBuilder;
    }

    @Override
    public void onPaymentFlowStarted() {
        Log.d("payment","started");
    }

    @Override
    public void onPaymentFlowStopped() {
        Log.d("payment","stopped");
    }

    @Override
    public void onPaymentSessionError(String errorMessage) {
        Log.d("payment-session-error",errorMessage);
    }

    @Override
    public void onPaymentSucceeded(String message) {
        Log.d("payment-success",message);
    }

    @Override
    public void onRequiresPaymentMethod(String message) {
        Log.d("payment-method-required",message);
    }

    @Override
    public void onPaymentFailed(String message) {
        Log.d("payment-failed",message);
    }
}
