package com.maluku.sma_rt.view.viewInterface

import com.maluku.sma_rt.model.persuratan.GetAllPersuratanItem
import com.maluku.sma_rt.model.persuratan.GetPersuratanById

interface WargaPersuratanInterface {
    fun onCreateSuccess(message: String)
    fun onCreateFailure(message: String)
    fun onUpdateSuccess(message: String)
    fun onUpdateFailure(message: String)
    fun onDeleteSuccess(message: String)
    fun onDeleteFailure(message: String)
    fun onGetDataSuccess(result: List<GetAllPersuratanItem>)
    fun onGetDataFailure(message: String)
    fun onGetDataByIDSuccess(data: GetPersuratanById?)
    fun onGetDataByIDFailure(message: String)
    fun onLetterReceivedSuccess(message: String)
    fun onLetterReceivedFailure(message: String)
    fun onLetterRejectedSuccess(message: String)
    fun onLetterRejectedFailure(message: String)
}
