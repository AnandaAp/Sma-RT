package com.maluku.sma_rt.model.rt

import com.google.gson.annotations.SerializedName

data class CreateRTAccountResponse(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("create_rt")
	val createRt: CreateRt? = null
)

data class CreateRt(

	@field:SerializedName("provinsi")
	val provinsi: String? = null,

	@field:SerializedName("keluarga")
	val keluarga: Any? = null,

	@field:SerializedName("kota")
	val kota: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("pengurus_rt")
	val pengurusRt: Any? = null,

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
)
