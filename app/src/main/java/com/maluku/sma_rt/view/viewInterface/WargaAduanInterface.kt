package com.maluku.sma_rt.view.viewInterface

import com.maluku.sma_rt.model.aduan.GetAduanById
import com.maluku.sma_rt.model.aduan.GetAllAduanItem

interface WargaAduanInterface {
    fun onCreateSuccess(message: String)
    fun onCreateFailed(message: String)
    fun onUpdateSuccess(message: String)
    fun onUpdateFailure(message: String)
    fun onDeleteSuccess(message: String)
    fun onDeleteFailure(message: String)
    fun onGetAllDataSuccess(list: List<GetAllAduanItem?>?)
    fun onGetAllDataFailed(message: String)
    fun onGetDataByIDSuccess(list: GetAduanById?)
    fun onGetDataByIDFailed(message: String)
    fun onReceiveComplaintSuccess(message: String)
    fun onReceiveComplaintFailure(message: String)
}