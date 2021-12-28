package com.maluku.sma_rt.view.viewInterface

interface LoginAdminInterface {
    fun loginAdmin(inputEmailAdmin: String,inputPasswordAdmin: String)
    fun onLoginSuccess(message: String)
    fun onLoginFailure(message: String)
}