package com.maluku.sma_rt.model.warga

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WargaGetDataLoginResponse(

	@field:SerializedName("get_warga_by_id")
	val getWargaById: GetMe? = null,

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("message")
	val message: String? = null
) : Parcelable

@Parcelize
data class GetMe(

	@field:SerializedName("password")
	val password: String? = null,

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("no_hp")
	val noHp: String? = null,

	@field:SerializedName("gender")
	val gender: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("id_keluarga")
	val idKeluarga: String? = null,

	@field:SerializedName("gambar")
	val gambar: String? = null,

	@field:SerializedName("email")
	val email: String? = null
) : Parcelable
