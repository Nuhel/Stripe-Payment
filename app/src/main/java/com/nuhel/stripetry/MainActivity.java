package com.nuhel.stripetry;

import androidx.annotation.Nullable;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.nuhel.stripetry.Stripe.StripePaymentActivity;
import com.nuhel.stripetry.Stripe.StripePayment;
import com.nuhel.stripetry.Stripe.StripePaymentBuilder;
import com.stripe.android.PaymentSession;
import com.stripe.android.model.PaymentMethod;
import com.stripe.android.view.AddPaymentMethodActivityStarter;
import com.stripe.android.view.PaymentMethodsActivityStarter;

public class MainActivity extends StripePaymentActivity {

    private PaymentSession paymentSession;
    private PaymentMethod paymentMethod;
    private Button payButton, addCard;
    private StripePayment st;
    private StripePaymentBuilder stripePaymentBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        payButton = findViewById(R.id.payButton);
        addCard = findViewById(R.id.addCard);



        //AndroidNetworking.initialize(this);

        try {
            stripePaymentBuilder = StripePayment.builder()
                    .setAppContext(getApplicationContext())
                    .setBaseUrl("https://devsite.airportshuttles.com/stripe/")
                    .setEphemeralKeyProviderUrl("createEphemeralKey.php")
                    .setClientSecretKeyUrlForPayment("makePayment.php")
                    .setCustomerId("Q1FE2Tx6MOOCoQjonty8aKjuad02")
                    .setPublishableKey("pk_test_51HYDPKKKrgZqoLaaBx4sFoLpni1rxR7rNKjzbom6Ur08muGttmXNvMFYmi4uvCcL4lPNkYJPuNtqQ3uKE2dIoVP500W1PI1sse")
            .build()
            .get();
            super.setStripePaymentBuilder(stripePaymentBuilder);
        } catch (Exception e) {
            Log.e("null",e.toString());
            e.printStackTrace();
        }


        payButton.setOnClickListener(v -> {
            PaymentMethodsActivityStarter.Args arg = new PaymentMethodsActivityStarter.Args.Builder()
                    .setCanDeletePaymentMethods(true)
                    //.setPaymentMethodsFooter(R.layout.footer_payment_method)
                    .build();

            new PaymentMethodsActivityStarter(MainActivity.this).startForResult(
                    arg
            );
        });

        addCard.setOnClickListener(v -> {
            AddPaymentMethodActivityStarter.Args arg = new AddPaymentMethodActivityStarter.Args.Builder()

                    //.setPaymentMethodsFooter(R.layout.footer_payment_method)
                    .build();

            new AddPaymentMethodActivityStarter(MainActivity.this).startForResult(
                    arg
            );
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.setAmount(900);
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onPaymentFlowStarted() {
        Log.d("payment","started");
        Toast.makeText(this, "Processing Your Payment", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPaymentFlowStopped() {
        Log.d("payment","stopped");
    }

    @Override
    public void onPaymentSucceeded(String message) {
        //Log.d("payment-success",message);
        Toast.makeText(this, "Payment Successful", Toast.LENGTH_SHORT).show();
    }





}