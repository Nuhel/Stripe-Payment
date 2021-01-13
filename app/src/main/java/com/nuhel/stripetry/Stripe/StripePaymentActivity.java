package com.nuhel.stripetry.Stripe;

import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.stripe.android.view.AddPaymentMethodActivityStarter;
import com.stripe.android.view.PaymentMethodsActivityStarter;

public class StripePaymentActivity extends AppCompatActivity {
    private StripePayment stripePayment;
    private int amount;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PaymentMethodsActivityStarter.REQUEST_CODE) {
            stripePayment = new StripePayment(this);
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

}
