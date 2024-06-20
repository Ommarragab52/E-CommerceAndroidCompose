package com.example.engaz.features.wallet.data.entities.stripe_payment

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class CreateEpheralKeyResponse(

	@field:SerializedName("associated_objects")
	val associatedObjects: List<AssociatedObjectsItem?>? = null,

	@field:SerializedName("expires")
	val expires: Int? = null,

	@field:SerializedName("livemode")
	val livemode: Boolean? = null,

	@field:SerializedName("created")
	val created: Int? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("secret")
	val secret: String? = null,

	@field:SerializedName("object")
	val objectt: String? = null
) : Parcelable

@Parcelize
data class AssociatedObjectsItem(

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("type")
	val type: String? = null
) : Parcelable
