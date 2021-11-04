package com.maluku.sma_rt.model.produk

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CreateProductResponse(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("create_produk")
	val createProduk: CreateProduk? = null,

	@field:SerializedName("message")
	val message: String? = null
) : Parcelable

@Parcelize
data class CreateProduk(

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
	val idKeluarga: String? = null
) : Parcelable
