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
import com.maluku.sma_rt.extentions.AdminSession
import com.maluku.sma_rt.extentions.UserValidator
import com.maluku.sma_rt.model.login.OnLoginSuccessResponse
import com.maluku.sma_rt.view.viewInterface.LoginAdminInterface
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
        Toast.makeText(activity,"$emailAdmin dan $password",Toast.LENGTH_LONG).show()
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
                            Toast.makeText(activity,"Pesan: ${response.message()}", Toast.LENGTH_SHORT).show()
                        }
                        false -> {
                            Toast.makeText(activity,"Pesan: ${response.message()}", Toast.LENGTH_SHORT).show()
                        }
                    }
                }

                override fun onFailure(call: Call<OnLoginSuccessResponse>, t: Throwable) {
                    Log.i(TAG, "onFailure: ${t.message}")
                }
            })
    }
    //save user data to session
    private fun saveSession(
        emailAdmin: String,
        password: String,
        token: String
    ) {
        val adminSession = AdminSession(activity)
        adminSession.save(AdminSession.SHARED_PREFERENCE_TOKEN_KEY,token)
        adminSession.save(AdminSession.SHARED_PREFERENCE_EMAIL_KEY,emailAdmin)
        adminSession.save(AdminSession.SHARED_PREFERENCE_PASSWORD_KEY,password)
        navigateToAdminDashboard()
    }

    //navigate to admin dashboard
    private fun navigateToAdminDashboard() {
//        Toast.makeText(activity,"Selamat Datang", Toast.LENGTH_LONG).show()
        Toast.makeText(activity,"Berhasil Login",Toast.LENGTH_LONG).show()
    }
}