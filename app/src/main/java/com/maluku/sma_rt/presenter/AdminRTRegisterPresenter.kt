package com.maluku.sma_rt.presenter

import android.app.Activity
import android.util.Log
import android.widget.Toast
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
        idAdminRT: String,
        idRT: String,
        namaAdmin: String,
//        alamatAdmin: String,
//        genderAdmin: String,
//        noHpAdmin: String,
        password: String,
//        confirmPassword: String,
        emailAdmin: String
//        kodePos: String
    ) {
        when (UserValidator.verifyData(emailAdmin, password, namaAdmin)) {
            true -> {
                pushToCloud(idAdminRT, idRT,emailAdmin,password,namaAdmin)
            }
        }
    }

    //push new data to cloud so user can register to server

    private fun pushToCloud(
        idAdminRT: String,
        idRT: String,
        namaAdmin: String,
//        alamatAdmin: String,
//        genderAdmin: String,
//        noHpAdmin: String,
        password: String,
//        confirmPassword: String,
        emailAdmin: String
//        kodePos: String
    ) {
        RetrofitService
            .getService()
            .signUpAdminRT(
                idAdminRT,
                idRT,
                namaAdmin,
                emailAdmin,
//                alamatAdmin,
//                genderAdmin,
                password
//                confirmPassword,
//                noHpAdmin,
//                kodePos
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
                                    idAdminRT,
                                    idRT,
                                    namaAdmin,
                                    emailAdmin,
//                                    alamatAdmin,
//                                    genderAdmin,
                                    password,
//                                    confirmPassword,
//                                    noHpAdmin,
//                                    kodePos,
                                    token
                                )
                            }
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
        idAdminRT: String,
        idRT: String,
        namaAdmin: String,
//        alamatAdmin: String,
//        genderAdmin: String,
//        noHpAdmin: String,
        password: String,
//        confirmPassword: String,
        emailAdmin: String,
//        kodePos: String,
        token: String
    ) {
        val adminSession = AdminSession(activity)
        adminSession.save(AdminSession.SHARED_PREFERENCE_TOKEN_KEY,token)
        adminSession.save(AdminSession.SHARED_PREFERENCE_ADMIN_ID_KEY,idAdminRT)
        adminSession.save(AdminSession.SHARED_PREFERENCE_ID_RT_KEY,idRT)
        adminSession.save(AdminSession.SHARED_PREFERENCE_NAME_KEY,namaAdmin)
//        adminSession.save(AdminSession.SHARED_PREFERENCE_EMAIL_KEY,alamatAdmin)
//        adminSession.save(AdminSession.SHARED_PREFERENCE_GENDER_KEY,genderAdmin)
//        adminSession.save(AdminSession.SHARED_PREFERENCE_PHONE_NUMBER_KEY,noHpAdmin)
        adminSession.save(AdminSession.SHARED_PREFERENCE_EMAIL_KEY,emailAdmin)
        adminSession.save(AdminSession.SHARED_PREFERENCE_PASSWORD_KEY,password)
//        adminSession.save(AdminSession.SHARED_PREFERENCE_PHONE_NUMBER_KEY,kodePos)
        navigateToAdminDashboard()
    }

    //navigate to admin dashboard
    private fun navigateToAdminDashboard() {
        Toast.makeText(activity,"Selamat Datang", Toast.LENGTH_LONG).show()
    }



}