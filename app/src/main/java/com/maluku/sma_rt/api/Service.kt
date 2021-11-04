package com.maluku.sma_rt.api

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
}