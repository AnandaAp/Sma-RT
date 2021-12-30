package com.maluku.sma_rt.view.viewInterface

interface AdminRTPasswordInterface {
    fun onChangePassSuccess(message: String)
    fun onChangePassFailure(message: String)
    fun onForgetPassSuccess(message: String)
    fun onForgetPassFailure(message: String)
    fun onResetPassSuccess(message: String)
    fun onResetPassFailure(message: String)
}