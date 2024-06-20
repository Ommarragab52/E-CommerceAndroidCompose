package com.example.data.api

import com.example.engaz.features.wallet.data.entities.stripe_payment.CreateCustomerResponse
import com.example.engaz.features.wallet.data.entities.stripe_payment.CreateEpheralKeyResponse
import com.example.engaz.features.wallet.data.entities.stripe_payment.CreatePaymentIntentResponse
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query
private const val SECRET ="sk_test_51Ow3KlRsJ1oPTnGIdqzmjns9YW6Um1aphHt1Q9Vj1OMyHdMb3Dmz05GfxLWhQM09MjCYQx9tz0qTrWcE0460JcUq00Kwxt0A7W"
interface StripeApiService {

    @Headers(
        "Authorization: Bearer $SECRET",
        "Stripe-Version: 2023-10-16"
    )
    @POST("v1/customers")
    suspend fun createCustomer() : CreateCustomerResponse

    @Headers(
        "Authorization: Bearer $SECRET",
        "Stripe-Version: 2023-10-16"
    )
    @POST("v1/ephemeral_keys")
    suspend fun createEphemeralKey(
        @Query("customer") customerId: String
    ): CreateEpheralKeyResponse

    @Headers(
        "Authorization: Bearer $SECRET"
    )
    @POST("v1/payment_intents")
    suspend fun createPaymentIntent(
        @Query("customer") customerId: String,
        @Query("amount") amount: Int,
        @Query("currency") currency: String,
        @Query("automatic_payment_methods[enabled]") autoPaymentMethodsEnable: Boolean,
    ): CreatePaymentIntentResponse

}