package com.maluku.sma_rt.presenter

import android.util.Log
import android.widget.Toast
import com.maluku.sma_rt.api.RetrofitService
import com.maluku.sma_rt.model.dompetkeluarga.*
import com.maluku.sma_rt.model.keluarga.GetKeluargaSayaResponse
import com.maluku.sma_rt.model.keluarga.UpdateKeluargaResponse
import com.maluku.sma_rt.view.viewInterface.DompetKeluargaInterface
import org.json.JSONObject
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
                    if (response.isSuccessful){
                        val result = response.body()?.getDompetKeluargaById
                        view.onGetDataSuccess(result)
                    } else{
                        val jObjError = JSONObject(response.errorBody()?.string())
                        val message = jObjError.getString("message")
                        view.onGetDataFailure(message!!)
                    }
                }
                override fun onFailure(call: Call<GetDompetKeluargaByIdResponse>, t: Throwable) {
                    view.onGetDataFailure(t.message.toString())
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
                    if (response.isSuccessful){
                        val result = response.body()?.getAllDompetKeluarga as List<GetAllDompetKeluargaItem>
                        view.onGetAllDataSuccess(result)
                    } else{
                        val jObjError = JSONObject(response.errorBody()?.string())
                        val message = jObjError.getString("message")
                        view.onGetAllDataFailed(message!!)
                    }
                }
                override fun onFailure(call: Call<GetAllDompetKeluargaResponse>, t: Throwable) {
                    view.onGetAllDataFailed(t.message.toString())
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
                    if (response.isSuccessful){
                        val result = response.body()?.getDompetKeluargaById
                        view.onGetDataSuccess(result)
                    } else{
                        val jObjError = JSONObject(response.errorBody()?.string())
                        val message = jObjError.getString("message")
                        view.onGetDataFailure(message!!)
                    }
                }
                override fun onFailure(call: Call<GetDompetKeluargaByIdResponse>, t: Throwable) {
                    view.onGetDataFailure(t.message.toString())
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
                    if (response.isSuccessful){
                        view.onTopupSuccess("Berhasil top up!")
                    } else {
                        val jObjError = JSONObject(response.errorBody()?.string())
                        val message = jObjError.getString("message")
                        view.onTopupFailure(message!!)
                    }
                }

                override fun onFailure(call: Call<DefaultDompetKeluargaResponse>, t: Throwable) {
                    view.onWithdrawFailure(t.message.toString())
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
                    if (response.isSuccessful){
                        view.onWithdrawSuccess("Berhasil withdraw!")
                    } else {
                        val jObjError = JSONObject(response.errorBody()?.string())
                        val message = jObjError.getString("message")
                        view.onWithdrawFailure(message)
                    }
                }

                override fun onFailure(call: Call<DefaultDompetKeluargaResponse>, t: Throwable) {
                    view.onWithdrawFailure(t.message.toString())
                }
            })
    }

}