package com.maluku.sma_rt.view.viewInterface

interface LoginWargaInterface {
    fun loginWarga(email: String, password: String)
    fun onLoginSuccess(message: String)
}