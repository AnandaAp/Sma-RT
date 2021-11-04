package com.maluku.sma_rt.presenter


import android.app.Activity
import android.util.Log
import android.widget.Toast
import com.maluku.sma_rt.api.RetrofitService
import com.maluku.sma_rt.extentions.UserSession
import com.maluku.sma_rt.extentions.UserSession.Companion.SHARED_PREFERENCE_EMAIL_KEY
import com.maluku.sma_rt.extentions.UserSession.Companion.SHARED_PREFERENCE_FAMILY_ID_KEY
import com.maluku.sma_rt.extentions.UserSession.Companion.SHARED_PREFERENCE_GENDER_KEY
import com.maluku.sma_rt.extentions.UserSession.Companion.SHARED_PREFERENCE_NAME_KEY
import com.maluku.sma_rt.extentions.UserSession.Companion.SHARED_PREFERENCE_PASSWORD_KEY
import com.maluku.sma_rt.extentions.UserSession.Companion.SHARED_PREFERENCE_PHONE_NUMBER_KEY
import com.maluku.sma_rt.extentions.UserSession.Companion.SHARED_PREFERENCE_TOKEN_KEY
import com.maluku.sma_rt.extentions.UserValidator
import com.maluku.sma_rt.model.warga.CreateWargaResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "REGISTER PRESENTER"
class WargaRegisterPresenter(private val activity: Activity) {

    //register new user
    fun registerNewUser(
        idKeluarga: String,
        email: String,
        password: String,
        name: String,
        gender: String,
        noHP: String) {
        when (UserValidator.verifyData(email, password, name)) {
            true -> {
                pushToCloud(idKeluarga, email, password, name, gender, noHP)
            }
        }
    }

    //push new data to cloud so user can register to server
    private fun pushToCloud(
        idKeluarga: String,
        email: String,
        password: String,
        name: String,
        gender: String,
        noHP: String
    ) {
        RetrofitService
            .getService()
            .signUpWarga(
                idKeluarga,
                name,
                email,
                password,
                noHP,
                gender)
            .enqueue(object : Callback<CreateWargaResponse> {
                override fun onResponse(
                    call: Call<CreateWargaResponse>,
                    response: Response<CreateWargaResponse>
                ) {
                    val token = response.body()?.token.toString()
                    when(response.isSuccessful){
                        true -> {
                            when(token.isNotEmpty()) {
                                true -> saveSession(name,
                                    idKeluarga,
                                    email,
                                    password,
                                    gender,
                                    noHP,
                                    token
                                )
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<CreateWargaResponse>, t: Throwable) {
                    Log.i(TAG, "onFailure: ${t.message}")
                }
            })
    }

    //save user data to session
    private fun saveSession(
        idKeluarga: String,
        name: String,
        email: String,
        password: String,
        gender: String,
        noHP: String,
        token: String
    ) {
        val userSession = UserSession(activity)
        userSession.save(SHARED_PREFERENCE_TOKEN_KEY,token)
        userSession.save(SHARED_PREFERENCE_NAME_KEY,name)
        userSession.save(SHARED_PREFERENCE_EMAIL_KEY,email)
        userSession.save(SHARED_PREFERENCE_PASSWORD_KEY,password)
        userSession.save(SHARED_PREFERENCE_GENDER_KEY,gender)
        userSession.save(SHARED_PREFERENCE_PHONE_NUMBER_KEY,noHP)
        userSession.save(SHARED_PREFERENCE_FAMILY_ID_KEY,idKeluarga)
        navigateToWargaDashboard()
    }

    //navigate to warga dashboard
    private fun navigateToWargaDashboard() {
        Toast.makeText(activity,"Selamat Datang",Toast.LENGTH_LONG).show()
    }
}