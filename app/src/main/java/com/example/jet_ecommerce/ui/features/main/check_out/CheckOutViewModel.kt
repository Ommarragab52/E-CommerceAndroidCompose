package com.example.jet_ecommerce.ui.features.main.check_out

import androidx.lifecycle.ViewModel
import com.example.engaz.features.wallet.data.repo.PaymentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class CheckOutViewModel @Inject constructor(
    private val repository: PaymentRepository
) : ViewModel() {



    suspend fun makePayment(onPaymentDetailsFetched: (clientSecret: String?, customerId: String?, ephemeralKey: String?) -> Unit) {
        val paymentIntentResponse = repository.createPaymentIntent()
        val ephemeralKeyResponse = repository.refreshCustomerEphemeralKey()
        val clientSecret = paymentIntentResponse.clientSecret
        val customerId = paymentIntentResponse.customer
        val ephemeralKey = ephemeralKeyResponse.secret
        onPaymentDetailsFetched(clientSecret, customerId, ephemeralKey)
    }




}