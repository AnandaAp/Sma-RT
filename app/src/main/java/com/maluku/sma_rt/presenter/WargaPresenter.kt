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
import com.maluku.sma_rt.model.password.DefaultPasswordResponse
import com.maluku.sma_rt.model.warga.CreateWargaResponse
import com.maluku.sma_rt.model.warga.WargaLoginResponse
import com.maluku.sma_rt.view.viewInterface.WargaInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import org.json.JSONObject
import retrofit2.awaitResponse


private const val TAG = "WARGA PRESENTER"
class WargaPresenter(private val activity: Activity, private val view: WargaInterface) {

    fun login(
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
                            view.onLoginSuccess("Selamat datang!")
                        }
                        false -> {
                            val jObjError = JSONObject(response.errorBody()?.string())
                            val message = jObjError.getString("message")
                            view.onLoginFailure(message)
                        }
                    }
                }

                override fun onFailure(call: Call<WargaLoginResponse>, t: Throwable) {
                    view.onLoginFailure(t.message.toString())
                    Log.i(TAG, "onFailure: ${t.message}")
                }
            })
    }

    fun create(
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
                                    email,
                                    password,
                                    token
                                )
                            }
                            view.onRegisterSuccess("Berhasil mendaftarkan akun!")
                        }
                        false -> {
                            val jObjError = JSONObject(response.errorBody()?.string())
                            val message = jObjError.getString("message")
                            view.onRegisterFailure(message)
                        }
                    }
                }

                override fun onFailure(call: Call<CreateWargaResponse>, t: Throwable) {
                    view.onRegisterFailure(t.message.toString())
                    Log.i(TAG, "onFailure: ${t.message}")
                }
            })
    }

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
    }

    fun getDataLogin(token: String) {
        GlobalScope.launch(Dispatchers.Main){
            val response = RetrofitService
                .getService()
                .getDataLoginWarga("Bearer $token")
                .awaitResponse()
            if (response.isSuccessful){
                val result = response.body()?.getWargaById
                view.onGetDataSuccess(result)
            } else{
                val jObjError = JSONObject(response.errorBody()!!.string())
                val message = jObjError.getString("message")
                view.onGetDataFailure(message)
            }
        }
    }

    fun update(
        token: String,
        wargaId: String,
        gender: String,
        no_hp: String,
        nama: String,
        email: String,
        gambar: String
    ) {
        GlobalScope.launch(Dispatchers.Main){
            val response = RetrofitService
                .getService()
                .updateWarga(
                    "Bearer $token",
                    wargaId,
                    gender,
                    no_hp,
                    nama,
                    email,
                    gambar
                ).awaitResponse()
            if (response.isSuccessful){
                view.onUpdateSuccess("Berhasil memperbaharui data!")
            } else {
                val jObjError = JSONObject(response.errorBody()!!.string())
                val message = jObjError.getString("message")
                view.onGetDataFailure(message)
            }
        }
    }

    fun changePassword(
        token: String,
        password: String,
        newPass: String,
    ){
        RetrofitService
            .getService()
            .changePasswordWarga(
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
                        view.onChangePasswordSuccess(message)
                    } else{
                        val jObjError = JSONObject(response.errorBody()?.string())
                        val message = jObjError.getString("message")
                        view.onChangePasswordFailure(message)
                    }
                }

                override fun onFailure(call: Call<DefaultPasswordResponse>, t: Throwable) {
                    view.onChangePasswordFailure(t.message.toString())
                }
            })
    }

}