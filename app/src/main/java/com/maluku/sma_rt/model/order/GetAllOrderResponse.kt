package com.maluku.sma_rt.model.order

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GetAllOrderResponse(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("get_all_order")
	val getAllOrder: List<GetAllOrderItem?>? = null,

	@field:SerializedName("message")
	val message: String? = null
) : Parcelable

@Parcelize
data class GetAllOrderItem(

	@field:SerializedName("pembayaran")
	val pembayaran: Pembayaran? = null,

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
	val idWarga: String? = null,

	@field:SerializedName("item_order")
	val itemOrder: List<ItemOrderItem?>? = null,

	@field:SerializedName("status")
	val status: String? = null
) : Parcelable

@Parcelize
data class ItemOrderItem(

	@field:SerializedName("id_produk")
	val idProduk: String? = null,

	@field:SerializedName("id_order")
	val idOrder: String? = null,

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

@Parcelize
data class Pembayaran(

	@field:SerializedName("id_order")
	val idOrder: String? = null,

	@field:SerializedName("jumlah_pembayaran")
	val jumlahPembayaran: Int? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("jenis")
	val jenis: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id_keluarga_penjual")
	val idKeluargaPenjual: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("id_keluarga_pembeli")
	val idKeluargaPembeli: String? = null,

	@field:SerializedName("status")
	val status: String? = null
) : Parcelable
