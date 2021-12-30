package com.maluku.sma_rt.presenter

import com.maluku.sma_rt.api.RetrofitService
import com.maluku.sma_rt.model.password.DefaultPasswordResponse
import com.maluku.sma_rt.view.viewInterface.AdminRTPasswordInterface
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminRTPasswordPresenter(private val view: AdminRTPasswordInterface) {
    // Ganti Password
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

    // Forget Password
    fun forgetPassAdmin(
        token: String,
        email: String
    ){
        RetrofitService
            .getService()
            .forgetPassAdmin(
                "Bearer $token",
                email
            )
            .enqueue(object : Callback<DefaultPasswordResponse> {
                override fun onResponse(
                    call: Call<DefaultPasswordResponse>,
                    response: Response<DefaultPasswordResponse>
                ) {
                    if (response.isSuccessful){
                        val message = response.body()?.message.toString()
                        view.onForgetPassSuccess(message)
                    } else{
                        val jObjError = JSONObject(response.errorBody()?.string())
                        val message = jObjError.getString("message")
                        view.onForgetPassFailure(message)
                    }
                }

                override fun onFailure(call: Call<DefaultPasswordResponse>, t: Throwable) {
                    view.onForgetPassFailure(t.message.toString())
                }
            })
    }

    // Reset Password
    fun resetPassAdmin(
        token: String,
        kode: String,
        password: String
    ){
        RetrofitService
            .getService()
            .resetPassAdmin(
                "Bearer $token",
                kode,
                password
            )
            .enqueue(object : Callback<DefaultPasswordResponse> {
                override fun onResponse(
                    call: Call<DefaultPasswordResponse>,
                    response: Response<DefaultPasswordResponse>
                ) {
                    if (response.isSuccessful){
                        val message = response.body()?.message.toString()
                        view.onResetPassSuccess(message)
                    } else{
                        val jObjError = JSONObject(response.errorBody()?.string())
                        val message = jObjError.getString("message")
                        view.onResetPassFailure(message)
                    }
                }

                override fun onFailure(call: Call<DefaultPasswordResponse>, t: Throwable) {
                    view.onResetPassFailure(t.message.toString())
                }
            })
    }
}