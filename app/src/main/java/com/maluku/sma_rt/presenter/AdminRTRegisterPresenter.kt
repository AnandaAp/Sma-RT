package com.maluku.sma_rt.presenter

import android.app.Activity
import android.util.Log
import android.widget.Toast
import com.maluku.sma_rt.R
import com.maluku.sma_rt.api.RetrofitService
import com.maluku.sma_rt.extentions.UserSession
import com.maluku.sma_rt.extentions.UserSession.Companion.SHARED_PREFERENCE_EMAIL_KEY
import com.maluku.sma_rt.extentions.UserSession.Companion.SHARED_PREFERENCE_GENDER_KEY
import com.maluku.sma_rt.extentions.UserSession.Companion.SHARED_PREFERENCE_ID_RT_KEY
import com.maluku.sma_rt.extentions.UserSession.Companion.SHARED_PREFERENCE_NAME_KEY
import com.maluku.sma_rt.extentions.UserSession.Companion.SHARED_PREFERENCE_PASSWORD_KEY
import com.maluku.sma_rt.extentions.UserSession.Companion.SHARED_PREFERENCE_PHONE_NUMBER_KEY
import com.maluku.sma_rt.extentions.UserSession.Companion.SHARED_PREFERENCE_ROLE_KEY
import com.maluku.sma_rt.extentions.UserSession.Companion.SHARED_PREFERENCE_TOKEN_FCM
import com.maluku.sma_rt.extentions.UserSession.Companion.SHARED_PREFERENCE_TOKEN_KEY
import com.maluku.sma_rt.extentions.UserValidator
import com.maluku.sma_rt.model.pengurus.CreatePengurusResponse
import com.maluku.sma_rt.view.viewInterface.RegisterAdminInterface
import org.json.JSONObject
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
        password: String,
        token_firebase: String
    ) {
        when (UserValidator.verifyData(emailAdmin, password, namaAdmin)) {
            true -> {
                pushToCloud(kodeRT, genderAdmin, noHpAdmin, namaAdmin,emailAdmin, password, token_firebase)
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
        password: String,
        token_firebase: String
    ) {
        RetrofitService
            .getService()
            .signUpAdminRT(
                kodeRT,
                genderAdmin,
                noHpAdmin,
                namaAdmin,
                emailAdmin,
                password,
                token_firebase
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
                                    token,
                                    token_firebase
                                )
                            }
                            Toast.makeText(activity,"Pesan: ${response.message()}",Toast.LENGTH_SHORT).show()
                            view.onRegisterSuccess(activity.getString(R.string.login_sukses))
                        }
                        false -> {
                            val jObjError = JSONObject(response.errorBody()?.string())
                            val message = jObjError.getString("message")
                            Toast.makeText(activity,"Pesan: $message", Toast.LENGTH_SHORT).show()
                            view.onRegisterFailure(activity.getString(R.string.login_gagal))
                        }
                    }
                }

                override fun onFailure(call: Call<CreatePengurusResponse>, t: Throwable) {
                    Log.i(TAG, "onFailure: ${t.message.toString()}")
                    view.onRegisterFailure(activity.getString(R.string.login_gagal))
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
        token: String,
        token_firebase: String
    ) {
        val userSession = UserSession(activity)
        userSession.save(SHARED_PREFERENCE_ROLE_KEY,"pengurus")
        userSession.save(SHARED_PREFERENCE_TOKEN_KEY,token)
        userSession.save(SHARED_PREFERENCE_ID_RT_KEY,kodeRT)
        userSession.save(SHARED_PREFERENCE_NAME_KEY,namaAdmin)
        userSession.save(SHARED_PREFERENCE_GENDER_KEY,genderAdmin)
        userSession.save(SHARED_PREFERENCE_PHONE_NUMBER_KEY,noHpAdmin)
        userSession.save(SHARED_PREFERENCE_EMAIL_KEY,emailAdmin)
        userSession.save(SHARED_PREFERENCE_PASSWORD_KEY,password)
        userSession.save(SHARED_PREFERENCE_TOKEN_FCM,token_firebase)
    }

}