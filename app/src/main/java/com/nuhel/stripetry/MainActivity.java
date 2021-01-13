package com.nuhel.stripetry;

import androidx.annotation.Nullable;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.androidnetworking.AndroidNetworking;
import com.nuhel.stripetry.Stripe.StripePaymentActivity;
import com.nuhel.stripetry.Stripe.StripePayment;
import com.stripe.android.PaymentSession;
import com.stripe.android.model.PaymentMethod;
import com.stripe.android.view.AddPaymentMethodActivityStarter;
import com.stripe.android.view.PaymentMethodsActivityStarter;

public class MainActivity extends StripePaymentActivity {

    private PaymentSession paymentSession;
    private PaymentMethod paymentMethod;
    private Button payButton, addCard;
    private StripePayment st;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        payButton = findViewById(R.id.payButton);
        addCard = findViewById(R.id.addCard);



        AndroidNetworking.initialize(this);


        try {
            StripePayment.init(getApplicationContext(),"Q1FE2Tx6MOOCoQjonty8aKjuad02");
        } catch (Exception e) {
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
        super.setAmount(500);
        super.onActivityResult(requestCode, resultCode, data);
        /*if (requestCode == PaymentMethodsActivityStarter.REQUEST_CODE) {
            st = new StripePayment(this);
            st.initPaymentSession(10,"",requestCode,resultCode,data);
        } else if (requestCode == AddPaymentMethodActivityStarter.REQUEST_CODE) {
             AddPaymentMethodActivityStarter.Result result = AddPaymentMethodActivityStarter.Result.fromIntent(data);
        } else if(StripePayment.PAYMENT_CONFIRM_REQUEST_CODE == requestCode ) {
            if(st != null){
                st.executePaymentResult(requestCode, data);
                st = null;
            }
        }*/
    }





}