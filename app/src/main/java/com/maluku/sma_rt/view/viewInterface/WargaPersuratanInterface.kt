package com.maluku.sma_rt.view.viewInterface

interface WargaPersuratanInterface {
    fun onCreateSuccess(message: String)
    fun onCreateFailure(message: String)
    fun onUpdateSuccess(message: String)
    fun onUpdateFailure(message: String)
    fun onDeleteSuccess(message: String)
    fun onDeleteFailure(message: String)
    fun onGetDataSuccess(message: String)
    fun onGetDataFailure(message: String)
}
