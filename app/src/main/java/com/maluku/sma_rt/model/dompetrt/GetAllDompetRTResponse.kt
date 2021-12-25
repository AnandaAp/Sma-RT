package com.maluku.sma_rt.model.dompetrt

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GetAllDompetRTResponse(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("get_all_dompet")
	val getAllDompet: List<GetAllDompetItem?>? = null,

	@field:SerializedName("message")
	val message: String? = null
) : Parcelable

@Parcelize
data class GetAllDompetItem(

	@field:SerializedName("id_rt")
	val idRt: String? = null,

	@field:SerializedName("jumlah")
	val jumlah: Int? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: String? = null
) : Parcelable
