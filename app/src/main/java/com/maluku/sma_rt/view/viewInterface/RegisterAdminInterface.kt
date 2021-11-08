package com.maluku.sma_rt.view.viewInterface

interface RegisterAdminInterface {
    fun registerAdmin(inputIdRT: String,inputNamaAdmin: String,inputAlamatAdmin: String,inputGenderAdmin: String,inputNoHpAdmin: String,inputConfirmPassword: String,inputPassword: String,inputKodePos: String)
    fun onRegisterSuccess(message: String)
}