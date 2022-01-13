package com.maluku.sma_rt.presenter

import com.maluku.sma_rt.api.RetrofitService
import com.maluku.sma_rt.model.keluarga.GetAllKeluargaWargaItem
import com.maluku.sma_rt.model.persuratan.GetAllPersuratanItem
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
    fun getAllDataSurat(
        token: String,
        status: String? = null
    ){
        GlobalScope.launch(Dispatchers.IO){
            /* jika status tidak kosong, maka akan memanggil methode service
                khusus pengelompokan
             */
            if(!status.isNullOrEmpty()){
                val response = RetrofitService
                    .getService()
                    .getAllPersuratanDataCategorically(
                        "Bearer $token",
                        status
                    ).awaitResponse()
                if(response.isSuccessful){
                    val result = response.body()?.getAllPersuratan as List<GetAllPersuratanItem>
                    view.onGetDataSuccess(result)
                }
                else{
                    val jObjError = JSONObject(response.errorBody()!!.string())
                    val message = jObjError.getString("message")
                    view.onGetDataFailure(message)
                }
            }
            else{
                val response = RetrofitService
                    .getService()
                    .getAllPersuratanData(
                        "Bearer $token"
                    ).awaitResponse()
                if(response.isSuccessful){
                    val result = response.body()?.getAllPersuratan as List<GetAllPersuratanItem>
                    view.onGetDataSuccess(result)
                }
                else{
                    val jObjError = JSONObject(response.errorBody()!!.string())
                    val message = jObjError.getString("message")
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
                    "Bearer $token",
                    id_surat
                ).awaitResponse()
            if(response.isSuccessful){
                val result = response.body()?.getPersuratanById
                view.onGetDataByIDSuccess(result)
            }
            else{
                val jObjError = JSONObject(response.errorBody()!!.string())
                val message = jObjError.getString("message")
                view.onGetDataByIDFailure(message)
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
                view.onDeleteSuccess(message)
            }
            else{
                val jObjError = JSONObject(response.errorBody()!!.string())
                val message = jObjError.getString("message")
                view.onDeleteFailure(message)
            }
        }
    }

    // Terima surat
    fun terimaSurat(
        token: String,
        idSurat: String,
        link: String
    ){
        GlobalScope.launch(Dispatchers.IO){
            val response = RetrofitService
                .getService()
                .terimaSurat(
                    "Bearer $token",
                    idSurat,
                    link
                ).awaitResponse()
            if(response.isSuccessful){
                val message = response.body()!!.message.toString()
                view.onLetterReceivedSuccess(message)
            }
            else{
                val jObjError = JSONObject(response.errorBody()!!.string())
                val message = jObjError.getString("message")
                view.onLetterReceivedFailure(message)
            }
        }
    }

    // Tolak surat
    fun tolakSurat(
        token: String,
        idSurat: String,
        alasan: String
    ){
        GlobalScope.launch(Dispatchers.IO){
            val response = RetrofitService
                .getService()
                .tolakSurat(
                    "Bearer $token",
                    idSurat,
                    alasan
                ).awaitResponse()
            if(response.isSuccessful){
                val message = response.body()!!.message.toString()
                view.onLetterReceivedSuccess(message)
            }
            else{
                val jObjError = JSONObject(response.errorBody()!!.string())
                val message = jObjError.getString("message")
                view.onLetterReceivedFailure(message)
            }
        }
    }
}