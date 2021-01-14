package com.nuhel.stripetry.Stripe;
import android.app.Application;
import android.content.Context;
import com.stripe.android.CustomerSession;
import com.stripe.android.PaymentConfiguration;

public class StripePaymentBuilder<T extends StripePaymentBuilder> {

    private Context appContext;
    private String customerId;
    private String baseUrl;
    private  String publishableKey ;
    private String ephemeralKeyProviderUrl;


    public StripePaymentBuilder(StripePaymentInstanceBuilder stripePaymentInstanceBuilder) {
        this.appContext = stripePaymentInstanceBuilder.appContext;
        this.customerId = stripePaymentInstanceBuilder.customerId;
        this.publishableKey = stripePaymentInstanceBuilder.publishableKey;
        this.baseUrl = stripePaymentInstanceBuilder.baseUrl;
        this.ephemeralKeyProviderUrl = stripePaymentInstanceBuilder.ephemeralKeyProviderUrl;

    }

    public StripePaymentBuilder get() {
        PaymentConfiguration.init(
                appContext,
                publishableKey
        );
        initCustomerSession();
        return (T) this;

    }

    public String getPublishableKey(){
        return this.publishableKey;
    }

    private void initCustomerSession() {
        CustomerSession.initCustomerSession(
                appContext,
                new EphemeralKeyBuilder(customerId,baseUrl,ephemeralKeyProviderUrl)
        );
    }


    public static class StripePaymentInstanceBuilder<T extends StripePaymentInstanceBuilder> {

        private Context appContext;
        public T setAppContext(Context appContext) throws Exception {
            if (appContext != null && appContext instanceof Application) {
                this.appContext = appContext;
            } else {
                throw new Exception("Context is missing");
            }
            return (T) this;
        }


        public T setBaseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return (T) this;
        }

        public T setEphemeralKeyProviderUrl(String ephemeralKeyProviderUrl) {
            this.ephemeralKeyProviderUrl = ephemeralKeyProviderUrl;
            return (T) this;
        }

        public T setCustomerId(String customerId) {
            this.customerId = customerId;
            return (T) this;
        }

        public T setPublishableKey(String publishableKey) {
            this.publishableKey = publishableKey;
            return (T) this;
        }

        public StripePaymentBuilder build() {
            return new StripePaymentBuilder(this);
        }
        private String customerId;
        private String publishableKey;
        private String baseUrl;
        private String ephemeralKeyProviderUrl;
    }
}
