package com.nuhel.stripetry.Stripe.callbacks;

import org.jetbrains.annotations.NotNull;

public interface PaymentSessionCallback {

    void onCommunicationChanged(boolean isCommunicating);
    void onSecretKeyGenerated(String paymentMethodId,String clientSecret);
    void onPaymentSessionError(int errorCode, @NotNull String errorMessage);
}
