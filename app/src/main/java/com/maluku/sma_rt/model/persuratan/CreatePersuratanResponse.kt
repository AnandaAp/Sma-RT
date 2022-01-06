package com.maluku.sma_rt.model.persuratan

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CreatePersuratanResponse(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("create_persuratan")
	val createPersuratan: CreatePersuratan? = null,

	@field:SerializedName("message")
	val message: String? = null
) : Parcelable

@Parcelize
data class CreatePersuratan(

	@field:SerializedName("id_rt")
	val idRt: String? = null,

	@field:SerializedName("keperluan")
	val keperluan: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("penerima")
	val penerima: String? = null,

	@field:SerializedName("link")
	val link: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("tanggal")
	val tanggal: String? = null,

	@field:SerializedName("judul")
	val judul: String? = null,

	@field:SerializedName("id_warga")
	val idWarga: String? = null,

	@field:SerializedName("status")
	val status: String? = null
) : Parcelable
