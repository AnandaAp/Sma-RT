package com.maluku.sma_rt.api

import com.maluku.sma_rt.model.pengurus.CreatePengurusResponse
import com.maluku.sma_rt.model.warga.CreateWargaResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface Service {

    @FormUrlEncoded
    @POST("warga")
    fun signUpWarga(
        @Field("id_keluarga") id_keluarga: String,
        @Field("nama") nama: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("no_hp") no_hp: String,
        @Field("gender") gender: String
    ): Call<CreateWargaResponse>

    @FormUrlEncoded
    @POST("pengurus")
    fun signUpAdminRT(
        @Field("id") id_admin_rt: String,
        @Field("id_rt") id_rt: String,
        @Field("nama") namaAdmin: String,
        @Field("email") emailAdmin: String,
//        @Field("alamat") alamatAdmin: String,
//        @Field("gender") genderAdmin: String,
        @Field("password") password: String,
//        @Field("confirm_password") confirmPassword: String,
//        @Field("no_hp") noHp: String,
//        @Field("kode_pos") kodePos: String
    ): Call<CreatePengurusResponse>
}