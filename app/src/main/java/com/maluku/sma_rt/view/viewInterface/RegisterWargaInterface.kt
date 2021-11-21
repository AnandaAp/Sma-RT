package com.maluku.sma_rt.view.viewInterface

interface RegisterWargaInterface {
    fun registerWarga(
        kode_keluarga: String,
        gender: String,
        no_hp: String,
        nama: String,
        email: String,
        password: String
    )
    fun onRegisterSuccess(message: String)
}