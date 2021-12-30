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
        /*RetrofitService
            .getService()
            .createAduan(
                "Bearer $token",
                judul,
                gambar,
                deskripsi
            ).enqueue(
                object : Callback<CreateAduanResponse>{
                    override fun onResponse(
                        call: Call<CreateAduanResponse>,
                        response: Response<CreateAduanResponse>
                    ) {
                        if (response.isSuccessful) {
                            val message = response.body()?.message.toString()
                            view.onCreateSuccess(message)
                        } else {
                            val jObjError = JSONObject(response.errorBody()?.string())
                            val message = jObjError.getString("message")
                            view.onCreateFailed(message)
                        }
                    }

                    override fun onFailure(call: Call<CreateAduanResponse>, t: Throwable) {
                        val message = t.message.toString()
                        view.onCreateFailed(message)
                    }
                }
            )*/
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
        /*RetrofitService
            .getService()
            .updateAduan(
                token,
                id,
                judul,
                gambar,
                deskripsi
            ).enqueue(
                object : Callback<OnDataResponse>{
                    override fun onResponse(
                        call: Call<OnDataResponse>,
                        response: Response<OnDataResponse>
                    ) {
                        val message = response.body()?.message.toString()
                        view.onUpdateSuccess(message)
                    }

                    override fun onFailure(call: Call<OnDataResponse>, t: Throwable) {
                        val message = t.message.toString()
                        view.onUpdateFailure(message)
                    }
                }
            )*/
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
        /*RetrofitService
            .getService()
            .deleteAduan(
                token,
                id
            ).enqueue(object : Callback<OnDataResponse>{
                override fun onResponse(
                    call: Call<OnDataResponse>,
                    response: Response<OnDataResponse>
                ) {
                    val message = response.body()?.message.toString()
                    view.onDeleteSuccess(message)
                }

                override fun onFailure(call: Call<OnDataResponse>, t: Throwable) {
                    val message = t.message.toString()
                    view.onDeleteFailure(message)
                }
            })*/
        GlobalScope.launch(Dispatchers.Main){
            val response = RetrofitService
                .getService()
                .deleteAduan(
                    token,
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
        /*RetrofitService
            .getService()
            .getAllAduan("Bearer $token")
            .enqueue(object : Callback<GetAllAduanResponse>{
                override fun onResponse(
                    call: Call<GetAllAduanResponse>,
                    response: Response<GetAllAduanResponse>
                ) {
                    if (response.isSuccessful){
                        val list = response.body()?.getAllAduan as List<GetAllAduanItem>
                        view.onGetAllDataSuccess(list)
                    } else{
                        val jObjError = JSONObject(response.errorBody()?.string())
                        val message = jObjError.getString("message")
                        view.onGetAllDataFailed(message)
                    }
                }

                override fun onFailure(call: Call<GetAllAduanResponse>, t: Throwable) {
                    val message = t.message.toString()
                    view.onGetAllDataFailed(message)
                }
            })*/
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

    fun getDataAduanByID(token: String,id: String){
        /*RetrofitService
            .getService()
            .getAduanByID(token,id)
            .enqueue(object : Callback<GetAduanByIDResponse>{
                override fun onResponse(
                    call: Call<GetAduanByIDResponse>,
                    response: Response<GetAduanByIDResponse>
                ) {
                    val list = response.body()?.getAduanById
                    view.onGetDataByIDSuccess(list)
                }

                override fun onFailure(call: Call<GetAduanByIDResponse>, t: Throwable) {
                    val message = t.message.toString()
                    view.onGetDataByIDFailed(message)
                }
            })*/
        GlobalScope.launch(Dispatchers.Main){
            val response = RetrofitService
                .getService()
                .getAduanByID(token,id)
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
}