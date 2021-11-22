package com.maluku.sma_rt.model.warga

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class GetWargaByIDResponse(

	@field:SerializedName("get_warga_by_id")
	val getWargaById: GetWargaById? = null,

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("message")
	val message: String? = null
) : Parcelable
