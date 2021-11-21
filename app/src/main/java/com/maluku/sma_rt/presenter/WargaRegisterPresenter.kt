package com.maluku.sma_rt.presenter


import android.app.Activity
import android.util.Log
import android.widget.TextView
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
        kode_keluarga: String,
        gender: String,
        no_hp: String,
        nama: String,
        email: String,
        password: String) {
        when (UserValidator.verifyData(email, password, nama)) {
            true -> {
                pushToCloud(
                    kode_keluarga,
                    gender,
                    no_hp,
                    nama,
                    email,
                    password)
            }
        }
    }

    //push new data to cloud so user can register to server
    private fun pushToCloud(
        kode_keluarga: String,
        gender: String,
        no_hp: String,
        nama: String,
        email: String,
        password: String
    ) {
        RetrofitService
            .getService()
            .signUpWarga(
                kode_keluarga,
                gender,
                no_hp,
                nama,
                email,
                password)
            .enqueue(object : Callback<CreateWargaResponse> {
                override fun onResponse(
                    call: Call<CreateWargaResponse>,
                    response: Response<CreateWargaResponse>
                ) {
                    val token = response.body()?.token.toString()
                    when(response.isSuccessful){
                        true -> {
                            when(token.isNotEmpty()) {
                                true -> saveSession(
                                    kode_keluarga,
                                    gender,
                                    no_hp,
                                    nama,
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

                override fun onFailure(call: Call<CreateWargaResponse>, t: Throwable) {
                    Log.i(TAG, "onFailure: ${t.message}")
                }
            })
    }

    //save user data to session
    private fun saveSession(
        kode_keluarga: String,
        gender: String,
        no_hp: String,
        nama: String,
        email: String,
        password: String,
        token: String
    ) {
        val userSession = UserSession(activity)
        userSession.save(SHARED_PREFERENCE_TOKEN_KEY,token)
        userSession.save(SHARED_PREFERENCE_NAME_KEY,nama)
        userSession.save(SHARED_PREFERENCE_EMAIL_KEY,email)
        userSession.save(SHARED_PREFERENCE_PASSWORD_KEY,password)
        userSession.save(SHARED_PREFERENCE_GENDER_KEY,gender)
        userSession.save(SHARED_PREFERENCE_PHONE_NUMBER_KEY,no_hp)
        userSession.save(SHARED_PREFERENCE_FAMILY_ID_KEY,kode_keluarga)
        //navigateToWargaDashboard()


        //memanggil fungsi dari interface view login warga

    }
}