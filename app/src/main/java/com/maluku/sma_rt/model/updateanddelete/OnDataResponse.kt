package com.maluku.sma_rt.model.updateanddelete

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


/*
	Data class yang dipakai untuk menampung response delete data
	ataupun update data dari retrofit
 */
@Parcelize
data class OnDataResponse(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("message")
	val message: String? = null
) : Parcelable
