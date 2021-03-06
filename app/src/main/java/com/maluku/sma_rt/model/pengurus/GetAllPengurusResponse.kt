package com.maluku.sma_rt.model.pengurus

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class GetAllPengurusResponse(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("get_all_pengurus")
	val getAllPengurus: List<GetAllPengurusItem?>? = null,

	@field:SerializedName("message")
	val message: String? = null
) : Parcelable

@Parcelize
data class GetAllPengurusItem(

	@field:SerializedName("id_rt")
	val idRt: String? = null,

	@field:SerializedName("password")
	val password: String? = null,

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("email")
	val email: String? = null
) : Parcelable
