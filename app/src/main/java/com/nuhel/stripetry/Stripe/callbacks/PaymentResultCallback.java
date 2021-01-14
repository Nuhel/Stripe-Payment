package com.nuhel.stripetry.Stripe.callbacks;

import org.jetbrains.annotations.NotNull;

public interface PaymentResultCallback {
    void onPaymentFlowStarted();
    void onPaymentFlowStopped();
    void onPaymentSessionError(String errorMessage);
    void onPaymentSucceeded(String message);
    void onRequiresPaymentMethod(String message);
    void onPaymentFailed(String message);

}
