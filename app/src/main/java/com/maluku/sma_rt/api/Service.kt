package com.maluku.sma_rt.api

import com.maluku.sma_rt.model.login.OnLoginSuccessResponse
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
        @Field("kode_rt") kode_rt: String,
        @Field("gender") genderAdmin: String,
        @Field("no_hp") noHpAdmin: String,
        @Field("nama") namaAdmin: String,
        @Field("email") emailAdmin: String,
        @Field("password") password: String
    ): Call<CreatePengurusResponse>

    @FormUrlEncoded
    @POST("pengurus/login")
    fun loginAdmin(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<OnLoginSuccessResponse>
}