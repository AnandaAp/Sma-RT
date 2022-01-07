package com.maluku.sma_rt.presenter

import com.maluku.sma_rt.api.RetrofitService
import com.maluku.sma_rt.view.viewInterface.WargaPersuratanInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.awaitResponse

class WargaPersuratanPresenter(private val view: WargaPersuratanInterface) {
    fun createPersuratan(
        token: String,
        judul: String,
        penerima: String,
        tanggal: String,
        keperluan: String
    ){
        GlobalScope.launch(Dispatchers.IO){
            val response = RetrofitService
                .getService()
                .createWargaPersuratan(
                    "Bearer ${token}",
                    judul,
                    penerima,
                    tanggal,
                    keperluan
                ).awaitResponse()
            if(response.isSuccessful){
                val message = response.body()!!.message.toString()
                view.onCreateSuccess(message)
            }
            else{
                val jObjError = JSONObject(response.errorBody()!!.string())
                val message = jObjError.getString("message")
                view.onCreateFailure(message)
            }
        }
    }

    fun updatePersuratan(
        token: String,
        id_surat: String,
        judul: String,
        penerima: String,
        tanggal: String,
        keperluan: String
    ){
        GlobalScope.launch(Dispatchers.IO){
            val response = RetrofitService
                .getService()
                .updateWargaPersuratan(
                    token,
                    id_surat,
                    judul,
                    penerima,
                    tanggal,
                    keperluan
                ).awaitResponse()
            if(response.isSuccessful){
                val message = response.body()!!.toString()
                view.onUpdateSuccess(message)
            }
            else{
                val message = response.errorBody()!!.string()
                view.onUpdateFailure(message)
            }
        }
    }


    /*status merupakan input opsi untuk mengambil seluruh data persuratan
        terdapat 4 nilai pada status:
        ""=tanpa pengelompokan,0=Tolak, 1=Terkirim, 2=Di Proses, 3=Selesai
     */
    fun getAllData(
        token: String,
        status: String = ""
    ){
        GlobalScope.launch(Dispatchers.IO){
            /* jika status tidak kosong, maka akan memanggil methode service
                khusus pengelompokan
             */
            if(status.isNotEmpty()){
                val response = RetrofitService
                    .getService()
                    .getAllPersuratanDataCategorically(
                        token,status
                    ).awaitResponse()
                if(response.isSuccessful){
                    val message = response.body()!!.message.toString()
                    view.onGetDataSuccess(message)
                }
                else{
                    val message = response.errorBody()!!.string()
                    view.onGetDataFailure(message)
                }
            }
            else{
                val response = RetrofitService
                    .getService()
                    .getAllPersuratanData(
                        token
                    ).awaitResponse()
                if(response.isSuccessful){
                    val message = response.body()!!.message.toString()
                    view.onGetDataSuccess(message)
                }
                else{
                    val message = response.errorBody()!!.string()
                    view.onGetDataFailure(message)
                }
            }
        }
    }

    fun getDataByID(
        token: String,
        id_surat: String
    ){
        GlobalScope.launch(Dispatchers.IO){
            val response = RetrofitService
                .getService()
                .getPersuratanDataByID(
                    token,
                    id_surat
                ).awaitResponse()
            if(response.isSuccessful){
                val message = response.body()!!.message.toString()
                view.onGetDataSuccess(message)
            }
            else{
                val message = response.errorBody()!!.string()
                view.onGetDataFailure(message)
            }
        }
    }

    fun deleteMail(
        token: String,
        id_surat: String
    ){
        GlobalScope.launch(Dispatchers.IO){
            val response = RetrofitService
                .getService()
                .deleteSuratByID(
                    token,
                    id_surat
                ).awaitResponse()
            if(response.isSuccessful){
                val message = response.body()!!.message.toString()
                view.onGetDataSuccess(message)
            }
            else{
                val message = response.errorBody()!!.string()
                view.onGetDataFailure(message)
            }
        }
    }
}