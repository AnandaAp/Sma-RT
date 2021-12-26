package com.maluku.sma_rt.model.dompetkeluarga

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DefaultDompetKeluargaResponse(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("message")
	val message: String? = null
) : Parcelable
