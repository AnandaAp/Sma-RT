package com.maluku.sma_rt.view.viewInterface

interface LoginAdminInterface {
    fun loginAdmin(inputUsernameAdmin: String,inputPasswordAdmin: String)
    fun onLoginSuccess(message: String)
}