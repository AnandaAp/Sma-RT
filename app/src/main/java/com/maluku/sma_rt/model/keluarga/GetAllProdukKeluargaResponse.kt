package com.maluku.sma_rt.model.keluarga

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GetAllProdukKeluargaResponse(
	val getAllProdukKeluarga: List<GetAllProdukKeluargaItem?>? = null,
	val code: Int? = null,
	val message: String? = null
) : Parcelable

@Parcelize
data class GetAllProdukKeluargaItem(
	val tersedia: Boolean? = null,
	val nama: String? = null,
	val harga: Int? = null,
	val updatedAt: String? = null,
	val createdAt: String? = null,
	val id: String? = null,
	val detail: String? = null,
	val idKeluarga: String? = null,
	val gambar: String? = null
) : Parcelable
