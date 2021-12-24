package com.maluku.sma_rt.model.keluarga

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GetAllProdukKeluargaResponse(

	@field:SerializedName("get_all_produk_keluarga")
	val getAllProdukKeluarga: List<GetAllProdukKeluargaItem?>? = null,

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("message")
	val message: String? = null
) : Parcelable

@Parcelize
data class GetAllProdukKeluargaItem(

	@field:SerializedName("tersedia")
	val tersedia: Boolean? = null,

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("harga")
	val harga: Int? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("detail")
	val detail: String? = null,

	@field:SerializedName("id_keluarga")
	val idKeluarga: String? = null,

	@field:SerializedName("gambar")
	val gambar: String? = null
) : Parcelable
