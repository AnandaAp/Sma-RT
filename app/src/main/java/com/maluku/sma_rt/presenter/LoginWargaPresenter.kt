package com.maluku.sma_rt.presenter

import android.app.Activity
import com.maluku.sma_rt.R
import com.maluku.sma_rt.api.RetrofitService
import com.maluku.sma_rt.extentions.UserSession
import com.maluku.sma_rt.model.warga.DetailLoginedWargaResponse
import com.maluku.sma_rt.model.warga.WargaLoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginWargaPresenter(private val activity: Activity) {

    //login user
    fun login(email: String, password: String){
        RetrofitService
            .getService()
            .signInWarga(email, password)
            .enqueue(object : Callback<WargaLoginResponse>{
                override fun onResponse(
                    call: Call<WargaLoginResponse>,
                    response: Response<WargaLoginResponse>
                ) {
                    val token = response.body()?.token.toString()
                    if(token.isNotEmpty()){
                        getUserProfile(token)
                    }
                }

                override fun onFailure(call: Call<WargaLoginResponse>, t: Throwable) {
                    val message = activity.getString(R.string.login_failed_message)
                }
            })
    }

    private fun getUserProfile(token: String) {
        RetrofitService
            .getService()
            .getMyData(token)
            .enqueue(object :Callback<DetailLoginedWargaResponse>{
                override fun onResponse(
                    call: Call<DetailLoginedWargaResponse>,
                    response: Response<DetailLoginedWargaResponse>
                ) {
                    val idKeluarga = response.body()?.getWargaById?.idKeluarga.toString()
                    val name = response.body()?.getWargaById?.nama.toString()
                    val email = response.body()?.getWargaById?.email.toString()
                    val password = response.body()?.getWargaById?.password.toString()
                    val gender = response.body()?.getWargaById?.gender.toString()
                    val noHP = response.body()?.getWargaById?.noHp.toString()
                    val message = response.message()
                    saveSession(
                        token,
                        idKeluarga,
                        name,
                        email,
                        password,
                        gender,
                        noHP,
                        message
                    )
                }

                override fun onFailure(call: Call<DetailLoginedWargaResponse>, t: Throwable) {

                }
            })
    }

    private fun saveSession(
        token: String,
        idKeluarga: String,
        name: String,
        email: String,
        password: String,
        gender: String,
        noHP: String,
        message: String
    ) {
        val userSession = UserSession(activity)
        userSession.save(UserSession.SHARED_PREFERENCE_TOKEN_KEY,token)
        userSession.save(UserSession.SHARED_PREFERENCE_NAME_KEY,name)
        userSession.save(UserSession.SHARED_PREFERENCE_EMAIL_KEY,email)
        userSession.save(UserSession.SHARED_PREFERENCE_PASSWORD_KEY,password)
        userSession.save(UserSession.SHARED_PREFERENCE_GENDER_KEY,gender)
        userSession.save(UserSession.SHARED_PREFERENCE_PHONE_NUMBER_KEY,noHP)
        userSession.save(UserSession.SHARED_PREFERENCE_FAMILY_ID_KEY,idKeluarga)

        //memanggil fungsi dari interface view login warga

    }
}