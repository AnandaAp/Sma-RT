package com.maluku.sma_rt.presenter

import android.util.Log
import android.widget.Toast
import com.maluku.sma_rt.api.RetrofitService
import com.maluku.sma_rt.model.dompetkeluarga.*
import com.maluku.sma_rt.model.keluarga.GetKeluargaSayaResponse
import com.maluku.sma_rt.model.keluarga.UpdateKeluargaResponse
import com.maluku.sma_rt.view.viewInterface.DompetKeluargaInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


private const val TAG = "DOMPET KELUARGA PRESENTER"

class DompetKeluargaPresenter(private var view: DompetKeluargaInterface) {
    fun getDompetKeluargaByLoginSession(token: String) {
        RetrofitService
            .getService()
            .getDompetKeluargaByLoginSession("Bearer $token")
            .enqueue(object : Callback<GetDompetKeluargaByIdResponse> {
                override fun onResponse(
                    call: Call<GetDompetKeluargaByIdResponse>,
                    response: Response<GetDompetKeluargaByIdResponse>
                ) {
                    val message = response.body()?.message
                    if (response.isSuccessful){
                        val result = response.body()?.getDompetKeluargaById
                        view.onGetDataSuccess(result)
                    } else{
                        view.onGetDataFailed(message!!)
                    }
                }
                override fun onFailure(call: Call<GetDompetKeluargaByIdResponse>, t: Throwable) {
                    view.onGetDataFailed("failed")
                }
            })
    }

    fun getAllDompetKeluarga(token: String) {
        RetrofitService
            .getService()
            .getAllDompetKeluarga("Bearer $token")
            .enqueue(object : Callback<GetAllDompetKeluargaResponse> {
                override fun onResponse(
                    call: Call<GetAllDompetKeluargaResponse>,
                    response: Response<GetAllDompetKeluargaResponse>
                ) {
                    val message = response.body()?.message
                    if (response.isSuccessful){
                        val result = response.body()?.getAllDompetKeluarga as List<GetAllDompetKeluargaItem>
                        view.onGetAllDataSuccess(result)
                    } else{
                        view.onGetAllDataFailed(message!!)
                    }
                }
                override fun onFailure(call: Call<GetAllDompetKeluargaResponse>, t: Throwable) {
                    view.onGetAllDataFailed("get all dompet keluarga failed")
                }
            })
    }

    fun getAllDompetKeluarga(
        token: String,
        idKeluarga: String
    ) {
        RetrofitService
            .getService()
            .getDompetKeluargaById(
                "Bearer $token",
                idKeluarga
            )
            .enqueue(object : Callback<GetDompetKeluargaByIdResponse> {
                override fun onResponse(
                    call: Call<GetDompetKeluargaByIdResponse>,
                    response: Response<GetDompetKeluargaByIdResponse>
                ) {
                    val message = response.body()?.message
                    if (response.isSuccessful){
                        val result = response.body()?.getDompetKeluargaById
                        view.onGetDataSuccess(result)
                    } else{
                        view.onGetDataFailed(message!!)
                    }
                }
                override fun onFailure(call: Call<GetDompetKeluargaByIdResponse>, t: Throwable) {
                    view.onGetDataFailed("get dompet keluarga by id failed")
                }
            })
    }

    fun topup(
        token: String,
        jumlah: String
    ) {
        RetrofitService
            .getService()
            .topUpDompetKeluarga(
                "Bearer $token",
                jumlah
            )
            .enqueue(object : Callback<DefaultDompetKeluargaResponse> {
                override fun onResponse(
                    call: Call<DefaultDompetKeluargaResponse>,
                    response: Response<DefaultDompetKeluargaResponse>
                ) {
                    val message = response.body()?.message
                    if (response.isSuccessful){
                        view.onTopupSuccess("Berhasil top up!")
                    } else {
                        view.onTopupFailure(message!!)
                    }
                }

                override fun onFailure(call: Call<DefaultDompetKeluargaResponse>, t: Throwable) {
                    view.onWithdrawFailure("top up failed")
                }
            })
    }

    fun withdraw(
        token: String,
        jumlah: String
    ) {
        RetrofitService
            .getService()
            .withdrawDompetKeluarga(
                "Bearer $token",
                jumlah
            )
            .enqueue(object : Callback<DefaultDompetKeluargaResponse> {
                override fun onResponse(
                    call: Call<DefaultDompetKeluargaResponse>,
                    response: Response<DefaultDompetKeluargaResponse>
                ) {
                    val message = response.body()?.message
                    if (response.isSuccessful){
                        view.onWithdrawSuccess("Berhasil withdraw!")
                    } else {
                        view.onWithdrawFailure(message!!)
                    }
                }

                override fun onFailure(call: Call<DefaultDompetKeluargaResponse>, t: Throwable) {
                    view.onWithdrawFailure("withdraw failed")
                }
            })
    }

}