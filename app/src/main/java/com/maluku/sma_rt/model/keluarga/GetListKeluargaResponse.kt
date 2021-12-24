package com.maluku.sma_rt.model.keluarga

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GetListKeluargaResponse(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("get_all_keluarga_warga")
	val getAllKeluargaWarga: List<GetAllKeluargaWargaItem?>? = null,

	@field:SerializedName("message")
	val message: String? = null
) : Parcelable

@Parcelize
data class DompetKeluarga(

	@field:SerializedName("jumlah")
	val jumlah: Int? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("id_keluarga")
	val idKeluarga: String? = null
) : Parcelable

@Parcelize
data class GetAllKeluargaWargaItem(

	@field:SerializedName("id_rt")
	val idRt: String? = null,

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("kode_keluarga")
	val kodeKeluarga: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("dompet_keluarga")
	val dompetKeluarga: DompetKeluarga? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("alamat")
	val alamat: String? = null,

	@field:SerializedName("warga")
	val warga: List<WargaItem?>? = null
) : Parcelable

@Parcelize
data class ForgetPasswordWarga(

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("kode")
	val kode: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("id_warga")
	val idWarga: String? = null
) : Parcelable

@Parcelize
data class WargaItem(

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

	@field:SerializedName("forget_password_warga")
	val forgetPasswordWarga: ForgetPasswordWarga? = null,

	@field:SerializedName("id_keluarga")
	val idKeluarga: String? = null,

	@field:SerializedName("gambar")
	val gambar: String? = null,

	@field:SerializedName("email")
	val email: String? = null
) : Parcelable
