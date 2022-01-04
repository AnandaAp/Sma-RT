package com.maluku.sma_rt.model.tagihan

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GetAllTagihanResponse(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("get_all_tagihan")
	val getAllTagihan: List<GetAllTagihanItem?>? = null,

	@field:SerializedName("message")
	val message: String? = null
) : Parcelable

@Parcelize
data class GetAllTagihanItem(

	@field:SerializedName("id_rt")
	val idRt: String? = null,

	@field:SerializedName("terbayar")
	val terbayar: String? = null,

	@field:SerializedName("nama_keluarga")
	val namaKeluarga: String? = null,

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("jumlah")
	val jumlah: Int? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("detail")
	val detail: String? = null,

	@field:SerializedName("id_keluarga")
	val idKeluarga: String? = null
) : Parcelable
