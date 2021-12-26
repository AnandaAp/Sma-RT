package com.maluku.sma_rt.model.dompetkeluarga

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GetAllDompetKeluargaResponse(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("get_all_dompet_keluarga")
	val getAllDompetKeluarga: List<GetAllDompetKeluargaItem?>? = null,

	@field:SerializedName("message")
	val message: String? = null
) : Parcelable

@Parcelize
data class GetAllDompetKeluargaItem(

	@field:SerializedName("jumlah")
	val jumlah: Int? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("id_keluarga")
	val idKeluarga: String? = null
) : Parcelable
