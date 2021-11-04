package com.maluku.sma_rt.view.viewInterface

interface RegisterTestInterface {
    fun register(idKeluarga: String, name: String,password: String,email: String,gender: String,noHP: String)
    fun onRegisterSuccess(message: String)
}