package com.maluku.sma_rt.view.viewInterface

import com.maluku.sma_rt.model.warga.GetMe

interface WargaInterface {
    fun onLoginSuccess(message: String)
    fun onLoginFailure(message: String)
    fun onRegisterSuccess(message: String)
    fun onRegisterFailure(message: String)
    fun onChangePasswordSuccess(message: String)
    fun onChangePasswordFailure(message: String)
    fun onUpdateSuccess(message: String)
    fun onUpdateFailure(message: String)
    fun onForgetPasswordSuccess(message: String)
    fun onForgetPasswordFailure(message: String)
    fun onResetPasswordSuccess(message: String)
    fun onResetPasswordFailure(message: String)
    fun onGetDataSuccess(result: GetMe?)
    fun onGetDataFailure(message: String)
}