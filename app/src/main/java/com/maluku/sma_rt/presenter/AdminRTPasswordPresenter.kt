package com.maluku.sma_rt.presenter

import com.maluku.sma_rt.api.RetrofitService
import com.maluku.sma_rt.model.password.DefaultPasswordResponse
import com.maluku.sma_rt.view.viewInterface.AdminRTPasswordInterface
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminRTPasswordPresenter(private val view: AdminRTPasswordInterface) {
    fun changePassAdmin(
        token: String,
        password: String,
        newPass: String,
    ){
        RetrofitService
            .getService()
            .changePassAdmin(
                "Bearer $token",
                password,
                newPass
            )
            .enqueue(object : Callback<DefaultPasswordResponse> {
                override fun onResponse(
                    call: Call<DefaultPasswordResponse>,
                    response: Response<DefaultPasswordResponse>
                ) {
                    if (response.isSuccessful){
                        val message = response.body()?.message.toString()
                        view.onChangePassSuccess(message)
                    } else{
                        val jObjError = JSONObject(response.errorBody()?.string())
                        val message = jObjError.getString("message")
                        view.onChangePassFailure(message)
                    }
                }

                override fun onFailure(call: Call<DefaultPasswordResponse>, t: Throwable) {
                    view.onChangePassFailure(t.message.toString())
                }
            })
    }
}