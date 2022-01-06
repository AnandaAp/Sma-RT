package com.maluku.sma_rt.view.viewInterface

interface RegisterAdminInterface {
    fun registerAdmin(
        inputKodeRT: String,
        inputGenderAdmin: String,
        inputNoHpAdmin: String,
        inputNamaAdmin: String,
        inputEmailAdmin: String,
        inputPassword: String
    )
    fun onRegisterSuccess(message: String)
    fun onRegisterFailure(message: String)
}