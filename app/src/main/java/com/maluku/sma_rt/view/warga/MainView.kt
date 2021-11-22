package com.maluku.sma_rt.view.warga

import com.maluku.sma_rt.base.BaseView

interface MainView: BaseView {
    fun success(hasil: String)
    fun error()
}