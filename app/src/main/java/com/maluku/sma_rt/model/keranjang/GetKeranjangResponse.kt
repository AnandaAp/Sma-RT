package com.maluku.sma_rt.model.keranjang

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GetKeranjangResponse(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("get_keranjang_by_id")
	val getKeranjangById: GetKeranjangById? = null,

	@field:SerializedName("message")
	val message: String? = null
) : Parcelable

@Parcelize
data class GetKeranjangById(

	@field:SerializedName("item_keranjang")
	val itemKeranjang: List<ItemKeranjangItem?>? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("harga_total")
	val hargaTotal: Int? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id_keluarga_penjual")
	val idKeluargaPenjual: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("id_warga")
	val idWarga: String? = null
) : Parcelable

@Parcelize
data class ItemKeranjangItem(

	@field:SerializedName("id_produk")
	val idProduk: String? = null,

	@field:SerializedName("id_keranjang")
	val idKeranjang: String? = null,

	@field:SerializedName("jumlah")
	val jumlah: Int? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("harga_total")
	val hargaTotal: Int? = null,

	@field:SerializedName("catatan")
	val catatan: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: String? = null
) : Parcelable
