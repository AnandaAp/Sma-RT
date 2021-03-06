package com.maluku.sma_rt.presenter

import com.maluku.sma_rt.api.RetrofitService
import com.maluku.sma_rt.model.aduan.CreateAduanResponse
import com.maluku.sma_rt.model.aduan.GetAduanByIDResponse
import com.maluku.sma_rt.model.aduan.GetAllAduanItem
import com.maluku.sma_rt.model.aduan.GetAllAduanResponse
import com.maluku.sma_rt.model.keluarga.GetAllKeluargaWargaItem
import com.maluku.sma_rt.model.updateanddelete.OnDataResponse
import com.maluku.sma_rt.view.viewInterface.WargaAduanInterface
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.awaitResponse

@DelicateCoroutinesApi
class WargaAduanPresenter(private val view: WargaAduanInterface) {

    fun createAduan(
        token: String,
        judul: String,
        gambar: String,
        deskripsi: String
    ){
        GlobalScope.launch(Dispatchers.Main){
            val res = RetrofitService
                .getService()
                .createAduan(
                    "Bearer $token",
                    judul,
                    gambar,
                    deskripsi
                ).awaitResponse()
            if (res.isSuccessful) {
                val message = res.body()?.message.toString()
                view.onCreateSuccess(message)
            } else {
                val jObjError = JSONObject(res.errorBody()!!.string())
                val message = jObjError.getString("message")
                view.onCreateFailed(message)
            }
        }
    }

    fun updateAduan(
        token: String,
        id: String,
        judul: String = "",
        gambar: String = "",
        deskripsi: String = ""
    ){
        GlobalScope.launch(Dispatchers.Main) {
            val response = RetrofitService
                .getService()
                .updateAduan(
                    token,
                    id,
                    judul,
                    gambar,
                    deskripsi
                ).awaitResponse()
            if(response.isSuccessful){
                val message = response.body()?.message.toString()
                view.onUpdateSuccess(message)
            }
            else{
                val message = response.errorBody()!!.string()
                view.onUpdateFailure(message)
            }
        }
    }

    fun deleteAduanByID(
        token: String,
        id: String
    ){
        GlobalScope.launch(Dispatchers.Main){
            val response = RetrofitService
                .getService()
                .deleteAduan(
                    "Bearer $token",
                    id
                ).awaitResponse()
            if(response.isSuccessful){
                val message = response.body()?.message.toString()
                view.onDeleteSuccess(message)
            }
            else{
                val message = response.errorBody()!!.string()
                view.onDeleteFailure(message)
            }
        }
    }

    fun getAllDataAduan(token: String){
        GlobalScope.launch(Dispatchers.Main){
            val response =  RetrofitService
                .getService()
                .getAllAduan("Bearer $token")
                .awaitResponse()
            if (response.isSuccessful){
                val list = response.body()?.getAllAduan as List<GetAllAduanItem>
                view.onGetAllDataSuccess(list)
            } else{
                val jObjError = JSONObject(response.errorBody()?.string())
                val message = jObjError.getString("message")
                view.onGetAllDataFailed(message)
            }
        }
    }

    fun getAllAduanRT(token: String){
        GlobalScope.launch(Dispatchers.Main){
            val response =  RetrofitService
                .getService()
                .getAllAduanRT("Bearer $token")
                .awaitResponse()
            if (response.isSuccessful){
                val list = response.body()?.getAllAduan as List<GetAllAduanItem>
                view.onGetAllDataSuccess(list)
            } else{
                val jObjError = JSONObject(response.errorBody()?.string())
                val message = jObjError.getString("message")
                view.onGetAllDataFailed(message)
            }
        }
    }

    fun getDataAduanByID(token: String,id: String){
        GlobalScope.launch(Dispatchers.Main){
            val response = RetrofitService
                .getService()
                .getAduanByID("Bearer $token",id)
                .awaitResponse()
            if(response.isSuccessful){
                val list = response.body()?.getAduanById
                view.onGetDataByIDSuccess(list)
            }
            else{
                val message = response.errorBody()!!.string()
                view.onGetDataByIDFailed(message)
            }
        }
    }

    fun terimaAduan(
        token: String,
        id_aduan: String)
    {
        GlobalScope.launch(Dispatchers.Main){
            val response = RetrofitService
                .getService()
                .terimaAduan(
                    "Bearer $token",
                    id_aduan
                )
                .awaitResponse()
            if(response.isSuccessful){
                val message = response.body()?.message.toString()
                view.onReceiveComplaintSuccess(message)
            }
            else{
                val jObjError = JSONObject(response.errorBody()?.string())
                val message = jObjError.getString("message")
                view.onReceiveComplaintFailure(message)
            }
        }
    }
}