package com.maluku.sma_rt.model.keluarga

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CreateKeluargaResponse(
    @field:SerializedName("code")
	val code: Int? = null,
    @field:SerializedName("create_keluarga")
	val createKeluarga: CreateKeluarga? = null,

    @field:SerializedName("message")
	val message: String? = null
) : Parcelable
@Parcelize
data class CreateKeluarga(

	@field:SerializedName("id_rt")
	val idRt: String? = null,

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: String? = null
) : Parcelable
