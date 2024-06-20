package com.example.engaz.features.wallet.data.repo

import com.example.data.api.StripeApiService
import com.example.engaz.features.wallet.data.entities.stripe_payment.CreateEpheralKeyResponse
import com.example.engaz.features.wallet.data.entities.stripe_payment.CreatePaymentIntentResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PaymentRepository @Inject constructor(
    private val stripeApiService: StripeApiService,
) {
    suspend fun createCustomer() = withContext(Dispatchers.IO) {
        val customer = stripeApiService.createCustomer()
        // save customer in the database or preferences
        // customerId required to confirm payment
    }

     suspend fun refreshCustomerEphemeralKey() : CreateEpheralKeyResponse{
         return  stripeApiService.createEphemeralKey("cus_QDebSe8pa1qyQD")
     }



    suspend fun createPaymentIntent() : CreatePaymentIntentResponse {
            return stripeApiService.createPaymentIntent(
                customerId = "cus_QDebSe8pa1qyQD",
                amount = 100,
                currency = "usd", // or your currency
                autoPaymentMethodsEnable = true
            )
        }
}
