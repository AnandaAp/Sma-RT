package com.maluku.sma_rt.presenter

import com.maluku.sma_rt.api.RetrofitService
//import com.maluku.sma_rt.model.dompetrt.DefaultDompetRTResponse
//import com.maluku.sma_rt.model.dompetrt.GetDompetRTByLoginResponse
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
import com.maluku.sma_rt.view.viewInterface.DompetRTInterface
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.awaitResponse

@DelicateCoroutinesApi
class DompetRTPresenter(private var view: DompetRTInterface) {
    fun getDompetRTByLogin(token:  String) {
        /*RetrofitService
            .getService()
            .getDompetRT("Bearer $token")
            .enqueue(object : Callback<GetDompetRTByLoginResponse> {
                override fun onResponse(
                    call: Call<GetDompetRTByLoginResponse>,
                    response: Response<GetDompetRTByLoginResponse>
                ) {
                    if (response.isSuccessful){
                        val result = response.body()?.getDompetById
                        view.onGetAllDataSuccess(result)
                    } else{
                        val jObjError = JSONObject(response.errorBody()?.string())
                        val message = jObjError.getString("message")
                        view.onGetAllDataFailure(message)
                    }
                }

                override fun onFailure(call: Call<GetDompetRTByLoginResponse>, t: Throwable) {
                    view.onGetAllDataFailure(t.message.toString())
                }
            })*/
        GlobalScope.launch(Dispatchers.Main){
            val response = RetrofitService
                .getService()
                .getDompetRT("Bearer $token")
                .awaitResponse()
            if (response.isSuccessful){
                val result = response.body()?.getDompetById
                view.onGetAllDataSuccess(result)
            } else{
                val jObjError = JSONObject(response.errorBody()!!.string())
                val message = jObjError.getString("message")
                view.onGetAllDataFailure(message)
            }
        }
    }

    fun withdrawAdmin(
        token: String,
        jumlah: String
    ) {
        /*RetrofitService
            .getService()
            .withdrawDompetRT(
                "Bearer $token",
                jumlah
            )
            .enqueue(object : Callback<DefaultDompetRTResponse> {
                override fun onResponse(
                    call: Call<DefaultDompetRTResponse>,
                    response: Response<DefaultDompetRTResponse>
                ) {
                    val message = response.body()?.message
                    if (response.isSuccessful){
                        view.onWithdrawSuccess(message!!)
                    } else {
                        val jObjError = JSONObject(response.errorBody()?.string())
                        val message = jObjError.getString("message")
                        view.onWithdrawFailure(message)
                    }
                }

                override fun onFailure(call: Call<DefaultDompetRTResponse>, t: Throwable) {
                    view.onWithdrawFailure("Withdraw failed")
                }
            })*/
        GlobalScope.launch(Dispatchers.Main){
            val response = RetrofitService
                .getService()
                .withdrawDompetRT(
                    "Bearer $token",
                    jumlah
                ).awaitResponse()
            var message = response.body()?.message
            if (response.isSuccessful){
                view.onWithdrawSuccess(message!!)
            } else {
                val jObjError = JSONObject(response.errorBody()!!.string())
                message = jObjError.getString("message")
                view.onWithdrawFailure(message)
            }
        }
    }
}