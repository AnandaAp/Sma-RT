package com.maluku.sma_rt.view.viewInterface

interface OrderInterface {
    fun onCreateOrderSuccess(message: String)
    fun onCreateOrderFailed(message: String)
}