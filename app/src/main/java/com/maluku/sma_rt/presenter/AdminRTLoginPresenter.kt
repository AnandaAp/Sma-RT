package com.maluku.sma_rt.presenter

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.maluku.sma_rt.R
import com.maluku.sma_rt.api.RetrofitService
import com.maluku.sma_rt.extentions.UserSession
import com.maluku.sma_rt.extentions.UserSession.Companion.SHARED_PREFERENCE_EMAIL_KEY
import com.maluku.sma_rt.extentions.UserSession.Companion.SHARED_PREFERENCE_PASSWORD_KEY
import com.maluku.sma_rt.extentions.UserSession.Companion.SHARED_PREFERENCE_ROLE_KEY
import com.maluku.sma_rt.extentions.UserSession.Companion.SHARED_PREFERENCE_TOKEN_KEY
import com.maluku.sma_rt.extentions.UserValidator
import com.maluku.sma_rt.model.login.OnLoginSuccessResponse
import com.maluku.sma_rt.view.viewInterface.LoginAdminInterface
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "LOGIN RT PRESENTER"
class AdminRTLoginPresenter(private val activity: Activity, private val view: LoginAdminInterface) {
    // Login Admin RT
    fun loginAdminPresenter(
        emailAdmin: String,
        password: String
    ) {
        pushToCloud(emailAdmin, password)
    }

    //push new data to cloud so user can register to server

    private fun pushToCloud(
        emailAdmin: String,
        password: String
    ) {

        RetrofitService
            .getService()
            .loginAdmin(
                emailAdmin,
                password
            )
            .enqueue(object : Callback<OnLoginSuccessResponse> {
                override fun onResponse(
                    call: Call<OnLoginSuccessResponse>,
                    response: Response<OnLoginSuccessResponse>
                ) {
                    val token = response.body()?.token.toString()
                    when(response.isSuccessful){
                        true -> {
                            when(token.isNotEmpty()) {
                                true -> saveSession(
                                    emailAdmin,
                                    password,
                                    token
                                )
                            }
                            view.onLoginSuccess(activity.getString(R.string.login_sukses))
                        }
                        false -> {
                            val jObjError = JSONObject(response.errorBody()?.string())
                            val message = jObjError.getString("message")
                            Toast.makeText(activity,"Pesan: $message", Toast.LENGTH_SHORT).show()
                            view.onLoginFailure(activity.getString(R.string.login_gagal))
                        }
                    }
                }

                override fun onFailure(call: Call<OnLoginSuccessResponse>, t: Throwable) {
                    Toast.makeText(activity,"Pesan: ${t.message.toString()}", Toast.LENGTH_SHORT).show()
                    view.onLoginFailure(activity.getString(R.string.login_gagal))
                }
            })
    }

    //save user data to session
    private fun saveSession(
        emailAdmin: String,
        password: String,
        token: String
    ) {
        val userSession = UserSession(activity)
        userSession.save(SHARED_PREFERENCE_ROLE_KEY,"pengurus")
        userSession.save(SHARED_PREFERENCE_TOKEN_KEY,token)
        userSession.save(SHARED_PREFERENCE_EMAIL_KEY,emailAdmin)
        userSession.save(SHARED_PREFERENCE_PASSWORD_KEY,password)
    }
}