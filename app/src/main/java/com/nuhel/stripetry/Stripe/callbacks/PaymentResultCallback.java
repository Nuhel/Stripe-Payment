package com.nuhel.stripetry.Stripe.callbacks;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nuhel.stripetry.MainActivity;
import com.stripe.android.ApiResultCallback;
import com.stripe.android.PaymentIntentResult;
import com.stripe.android.PaymentSession;
import com.stripe.android.model.PaymentIntent;

import java.lang.ref.WeakReference;

public class PaymentResultCallback

        implements ApiResultCallback<PaymentIntentResult> {

    @NonNull
    private final WeakReference<Activity> activityRef;

    public PaymentResultCallback(@NonNull Activity activity) {

        activityRef = new WeakReference<>(activity);

    }

    @Override

    public void onSuccess(@NonNull PaymentIntentResult result) {

        final Activity activity = activityRef.get();

        if (activity == null) {
            return;
        }


        PaymentIntent paymentIntent = result.getIntent();

        PaymentIntent.Status status = paymentIntent.getStatus();

        if (status == PaymentIntent.Status.Succeeded) {

            // Payment completed successfully

            Gson gson = new GsonBuilder().setPrettyPrinting().create();

                /*activity.displayAlert(

                        "Payment completed",

                        gson.toJson(paymentIntent)

                );*/

            Log.e("log-success", "Payment completed");

        } else if (status == PaymentIntent.Status.RequiresPaymentMethod) {

            // Payment failed – allow retrying using a different payment method

                /*activity.displayAlert(

                        "Payment failed",

                        Objects.requireNonNull(paymentIntent.getLastPaymentError()).getMessage()

                );*/

            Log.e("log-failed", "Payment failed");

        }

    }

    @Override

    public void onError(@NonNull Exception e) {

        final Activity activity = activityRef.get();

        if (activity == null) {

            return;

        }

        // Payment request failed – allow retrying using the same payment method

        //activity.displayAlert("Error", e.toString());
        Log.e("log-unknown", e.toString());

    }

}
