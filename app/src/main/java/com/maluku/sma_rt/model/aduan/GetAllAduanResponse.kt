package com.maluku.sma_rt.model.aduan

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GetAllAduanResponse(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("get_all_aduan")
	val getAllAduan: ArrayList<GetAllAduanItem?>? = null,

	@field:SerializedName("message")
	val message: String? = null
) : Parcelable

@Parcelize
data class GetAllAduanItem(

	@field:SerializedName("id_rt")
	val idRt: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("createdBy")
	val createdBy: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("deskripsi")
	val deskripsi: String? = null,

	@field:SerializedName("judul")
	val judul: String? = null,

	@field:SerializedName("gambar")
	val gambar: String? = null,

	@field:SerializedName("id_warga")
	val idWarga: String? = null
) : Parcelable
