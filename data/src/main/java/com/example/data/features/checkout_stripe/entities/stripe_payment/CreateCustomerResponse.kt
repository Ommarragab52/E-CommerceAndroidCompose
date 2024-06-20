package com.example.engaz.features.wallet.data.entities.stripe_payment

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class CreateCustomerResponse(

	@field:SerializedName("invoice_settings")
	val invoiceSettings: InvoiceSettings? = null,

	@field:SerializedName("metadata")
	val metadata: Metadata? = null,

	@field:SerializedName("test_clock")
	val testClock: Int? = null,

	@field:SerializedName("address")
	val address: String? = null,

	@field:SerializedName("livemode")
	val livemode: Boolean? = null,

	@field:SerializedName("default_source")
	val defaultSource: String? = null,

	@field:SerializedName("invoice_prefix")
	val invoicePrefix: String? = null,

	@field:SerializedName("tax_exempt")
	val taxExempt: String? = null,

	@field:SerializedName("created")
	val created: Int? = null,

	@field:SerializedName("next_invoice_sequence")
	val nextInvoiceSequence: Int? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("discount")
	val discount: String? = null,

	@field:SerializedName("preferred_locales")
	val preferredLocales: List<String?>? = null,

	@field:SerializedName("balance")
	val balance: Int? = null,

	@field:SerializedName("shipping")
	val shipping: String? = null,

	@field:SerializedName("phone")
	val phone: String? = null,

	@field:SerializedName("delinquent")
	val delinquent: Boolean? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("currency")
	val currency: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("object")
	val objectt: String? = null
) : Parcelable

@Parcelize
data class InvoiceSettings(

	@field:SerializedName("footer")
	val footer: String? = null,

	@field:SerializedName("custom_fields")
	val customFields: String? = null,

	@field:SerializedName("default_payment_method")
	val defaultPaymentMethod: String? = null,

	@field:SerializedName("rendering_options")
	val renderingOptions: String? = null
) : Parcelable

@Parcelize
data class Metadata(
	val any: String? = null
) : Parcelable
