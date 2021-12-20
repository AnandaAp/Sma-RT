package com.maluku.sma_rt.model.tagihan

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CreateTagihanResponse(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("create_tagihan")
	val createTagihan: CreateTagihan? = null,

	@field:SerializedName("message")
	val message: String? = null
) : Parcelable

@Parcelize
data class CreateTagihan(

	@field:SerializedName("id_rt")
	val idRt: String? = null,

	@field:SerializedName("terbayar")
	val terbayar: Boolean? = null,

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
