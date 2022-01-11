package com.maluku.sma_rt.model.persuratan

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PersuratanDefaultResponse(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("message")
	val message: String? = null
) : Parcelable
