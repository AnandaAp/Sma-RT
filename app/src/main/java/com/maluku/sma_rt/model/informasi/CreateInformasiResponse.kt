package com.maluku.sma_rt.model.informasi

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CreateInformasiResponse(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("create_informasi")
	val createInformasi: CreateInformasi? = null,

	@field:SerializedName("message")
	val message: String? = null
) : Parcelable

@Parcelize
data class CreateInformasi(

	@field:SerializedName("id_rt")
	val idRt: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("lokasi")
	val lokasi: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("kategori")
	val kategori: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("detail")
	val detail: String? = null,

	@field:SerializedName("judul")
	val judul: String? = null,

	@field:SerializedName("gambar")
	val gambar: String? = null,

	@field:SerializedName("created_by")
	val createdBy: String? = null
) : Parcelable
