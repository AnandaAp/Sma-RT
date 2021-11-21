package com.maluku.sma_rt.base

interface BasePresenter<in T: BaseView> {
    fun onAttach(view: T)
    fun onDettach()
}