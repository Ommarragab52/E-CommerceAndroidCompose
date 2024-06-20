package com.example.engaz.features.wallet.data.entities.stripe_payment

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class CreatePaymentIntentResponse(

	@field:SerializedName("amount_details")
	val amountDetails: AmountDetails? = null,

	@field:SerializedName("metadata")
	val metadata: Metadata? = null,

	@field:SerializedName("livemode")
	val livemode: Boolean? = null,

	@field:SerializedName("canceled_at")
	val canceledAt: String? = null,

	@field:SerializedName("amount_capturable")
	val amountCapturable: Int? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("source")
	val source: String? = null,

	@field:SerializedName("statement_descriptor")
	val statementDescriptor: String? = null,

	@field:SerializedName("transfer_data")
	val transferData: String? = null,

	@field:SerializedName("latest_charge")
	val latestCharge: String? = null,

	@field:SerializedName("shipping")
	val shipping: String? = null,

	@field:SerializedName("automatic_payment_methods")
	val automaticPaymentMethods: AutomaticPaymentMethods? = null,

	@field:SerializedName("review")
	val review: String? = null,

	@field:SerializedName("currency")
	val currency: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("client_secret")
	val clientSecret: String? = null,

	@field:SerializedName("payment_method_options")
	val paymentMethodOptions: PaymentMethodOptions? = null,

	@field:SerializedName("payment_method")
	val paymentMethod: String? = null,

	@field:SerializedName("capture_method")
	val captureMethod: String? = null,

	@field:SerializedName("amount")
	val amount: Int? = null,

	@field:SerializedName("transfer_group")
	val transferGroup: String? = null,

	@field:SerializedName("on_behalf_of")
	val onBehalfOf: String? = null,

	@field:SerializedName("created")
	val created: Int? = null,

	@field:SerializedName("payment_method_types")
	val paymentMethodTypes: List<String?>? = null,

	@field:SerializedName("amount_received")
	val amountReceived: Int? = null,

	@field:SerializedName("setup_future_usage")
	val setupFutureUsage: String? = null,

	@field:SerializedName("confirmation_method")
	val confirmationMethod: String? = null,

	@field:SerializedName("cancellation_reason")
	val cancellationReason: String? = null,

	@field:SerializedName("payment_method_configuration_details")
	val paymentMethodConfigurationDetails: PaymentMethodConfigurationDetails? = null,

	@field:SerializedName("application")
	val application: String? = null,

	@field:SerializedName("receipt_email")
	val receiptEmail: String? = null,

	@field:SerializedName("last_payment_error")
	val lastPaymentError: String? = null,

	@field:SerializedName("next_action")
	val nextAction: String? = null,

	@field:SerializedName("processing")
	val processing: String? = null,

	@field:SerializedName("invoice")
	val invoice: String? = null,

	@field:SerializedName("statement_descriptor_suffix")
	val statementDescriptorSuffix: String? = null,

	@field:SerializedName("application_fee_amount")
	val applicationFeeAmount: String? = null,

	@field:SerializedName("object")
	val objectt: String? = null,

	@field:SerializedName("customer")
	val customer: String? = null,

	@field:SerializedName("status")
	val status: String? = null
) : Parcelable

@Parcelize
data class Link(

	@field:SerializedName("persistent_token")
	val persistentToken: String? = null
) : Parcelable

@Parcelize
data class AutomaticPaymentMethods(

	@field:SerializedName("allow_redirects")
	val allowRedirects: String? = null,

	@field:SerializedName("enabled")
	val enabled: Boolean? = null
) : Parcelable

@Parcelize
data class Card(

	@field:SerializedName("installments")
	val installments: String? = null,

	@field:SerializedName("request_three_d_secure")
	val requestThreeDSecure: String? = null,

	@field:SerializedName("mandate_options")
	val mandateOptions: String? = null,

	@field:SerializedName("network")
	val network: String? = null
) : Parcelable

@Parcelize
data class Tip(
	val any: String? = null
) : Parcelable

@Parcelize
data class AmountDetails(

	@field:SerializedName("tip")
	val tip: Tip? = null
) : Parcelable

@Parcelize
data class PaymentMethodConfigurationDetails(

	@field:SerializedName("parent")
	val parent: String? = null,

	@field:SerializedName("id")
	val id: String? = null
) : Parcelable

@Parcelize
data class MetadataPaymentIntent(
	val any: String? = null
) : Parcelable

@Parcelize
data class PaymentMethodOptions(

	@field:SerializedName("link")
	val link: Link? = null,

	@field:SerializedName("card")
	val card: Card? = null
) : Parcelable
