package com.maluku.sma_rt.model.rt

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class GetAllRTResponse(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("get_all_rt")
	val getAllRt: List<GetAllRtItem?>? = null,

	@field:SerializedName("message")
	val message: String? = null
) : Parcelable

@Parcelize
data class GetAllRtItem(

	@field:SerializedName("provinsi")
	val provinsi: String? = null,

	@field:SerializedName("keluarga")
	val keluarga: String? = null,

	@field:SerializedName("kota")
	val kota: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("pengurus_rt")
	val pengurusRt: String? = null,

	@field:SerializedName("kecamatan")
	val kecamatan: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("nama_rt")
	val namaRt: String? = null,

	@field:SerializedName("nama_rw")
	val namaRw: String? = null,

	@field:SerializedName("biaya_bulanan")
	val biayaBulanan: Int? = null,

	@field:SerializedName("kelurahan")
	val kelurahan: String? = null
) : Parcelable
