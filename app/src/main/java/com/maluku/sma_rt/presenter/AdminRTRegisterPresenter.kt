package com.maluku.sma_rt.presenter

import android.app.Activity
import android.util.Log
import android.widget.Toast
import com.maluku.sma_rt.R
import com.maluku.sma_rt.api.RetrofitService
import com.maluku.sma_rt.extentions.AdminSession
import com.maluku.sma_rt.extentions.UserValidator
import com.maluku.sma_rt.model.pengurus.CreatePengurusResponse
import com.maluku.sma_rt.view.viewInterface.RegisterAdminInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "REGISTER RT PRESENTER"

class AdminRTRegisterPresenter(private val activity: Activity, private val view: RegisterAdminInterface) {
    //register new user
    fun registerNewAdmin(
        kodeRT: String,
        genderAdmin: String,
        noHpAdmin: String,
        namaAdmin: String,
        emailAdmin: String,
        password: String
    ) {
        when (UserValidator.verifyData(emailAdmin, password, namaAdmin)) {
            true -> {
                pushToCloud(kodeRT, genderAdmin, noHpAdmin, namaAdmin,emailAdmin, password)
            }
        }
    }

    //push new data to cloud so user can register to server

    private fun pushToCloud(
        kodeRT: String,
        genderAdmin: String,
        noHpAdmin: String,
        namaAdmin: String,
        emailAdmin: String,
        password: String
    ) {
        RetrofitService
            .getService()
            .signUpAdminRT(
                kodeRT,
                genderAdmin,
                noHpAdmin,
                namaAdmin,
                emailAdmin,
                password
            )
            .enqueue(object : Callback<CreatePengurusResponse> {
                override fun onResponse(
                    call: Call<CreatePengurusResponse>,
                    response: Response<CreatePengurusResponse>
                ) {
                    val token = response.body()?.token.toString()
                    when(response.isSuccessful){
                        true -> {
                            when(token.isNotEmpty()) {
                                true -> saveSession(
                                    kodeRT,
                                    genderAdmin,
                                    noHpAdmin,
                                    namaAdmin,
                                    emailAdmin,
                                    password,
                                    token
                                )
                            }
                            Toast.makeText(activity,"Pesan: ${response.message()}",Toast.LENGTH_SHORT).show()
                            view.onRegisterSuccess(activity.getString(R.string.login_sukses))
                        }
                        false -> {
                            Toast.makeText(activity,"Pesan: ${response.message()}",Toast.LENGTH_SHORT).show()
                        }
                    }
                }

                override fun onFailure(call: Call<CreatePengurusResponse>, t: Throwable) {
                    Log.i(TAG, "onFailure: ${t.message}")
                }
            })
    }
    //save user data to session
    private fun saveSession(
        kodeRT: String,
        genderAdmin: String,
        noHpAdmin: String,
        namaAdmin: String,
        emailAdmin: String,
        password: String,
        token: String
    ) {
        val adminSession = AdminSession(activity)
        adminSession.save(AdminSession.SHARED_PREFERENCE_TOKEN_KEY,token)
        adminSession.save(AdminSession.SHARED_PREFERENCE_ID_RT_KEY,kodeRT)
        adminSession.save(AdminSession.SHARED_PREFERENCE_NAME_KEY,namaAdmin)
        adminSession.save(AdminSession.SHARED_PREFERENCE_GENDER_KEY,genderAdmin)
        adminSession.save(AdminSession.SHARED_PREFERENCE_PHONE_NUMBER_KEY,noHpAdmin)
        adminSession.save(AdminSession.SHARED_PREFERENCE_EMAIL_KEY,emailAdmin)
        adminSession.save(AdminSession.SHARED_PREFERENCE_PASSWORD_KEY,password)
    }

}