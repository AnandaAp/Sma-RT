package com.maluku.sma_rt.presenter

import android.app.Activity
import android.util.Log
import android.widget.Toast
import com.maluku.sma_rt.R
import com.maluku.sma_rt.api.RetrofitService
import com.maluku.sma_rt.extentions.UserSession
import com.maluku.sma_rt.extentions.UserSession.Companion.SHARED_PREFERENCE_EMAIL_KEY
import com.maluku.sma_rt.extentions.UserSession.Companion.SHARED_PREFERENCE_PASSWORD_KEY
import com.maluku.sma_rt.extentions.UserSession.Companion.SHARED_PREFERENCE_ROLE_KEY
import com.maluku.sma_rt.extentions.UserSession.Companion.SHARED_PREFERENCE_TOKEN_KEY
import com.maluku.sma_rt.model.warga.WargaLoginResponse
import com.maluku.sma_rt.view.viewInterface.LoginWargaInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "LOGIN PRESENTER"
class WargaLoginPresenter(private val activity: Activity, private val view: LoginWargaInterface) {

    //register new user
    fun loginUser(
        email: String,
        password: String) {
            pushToCloud(
                email,
                password)
    }

    //push new data to cloud so user can register to server
    private fun pushToCloud(
        email: String,
        password: String
    ) {
        RetrofitService
            .getService()
            .signInWarga(
                email,
                password)
            .enqueue(object : Callback<WargaLoginResponse> {
                override fun onResponse(
                    call: Call<WargaLoginResponse>,
                    response: Response<WargaLoginResponse>
                ) {
                    val token = response.body()?.token.toString()
                    when(response.isSuccessful){
                        true -> {
                            when(token.isNotEmpty()) {
                                true -> saveSession(
                                    email,
                                    password,
                                    token
                                )
                            }
                            Toast.makeText(activity,"Pesan: ${response.message()}",Toast.LENGTH_SHORT).show()
                        }
                        false -> {
                            Toast.makeText(activity,"Error: ${response.message()}",Toast.LENGTH_SHORT).show()
                        }
                    }
                }

                override fun onFailure(call: Call<WargaLoginResponse>, t: Throwable) {
                    Log.i(TAG, "onFailure: ${t.message}")
                }
            })
    }

    //save user data to session
    private fun saveSession(
        email: String,
        password: String,
        token: String
    ) {
        val userSession = UserSession(activity)
        userSession.save(SHARED_PREFERENCE_ROLE_KEY,"warga")
        userSession.save(SHARED_PREFERENCE_TOKEN_KEY,token)
        userSession.save(SHARED_PREFERENCE_EMAIL_KEY,email)
        userSession.save(SHARED_PREFERENCE_PASSWORD_KEY,password)
        view.onLoginSuccess(activity.getString(R.string.login_sukses))
    }

}