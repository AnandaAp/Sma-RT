package com.maluku.sma_rt.api

import com.maluku.sma_rt.api.notification.Constants
import com.maluku.sma_rt.api.notification.PushNotification
import com.maluku.sma_rt.api.notification.SendNotification
import com.maluku.sma_rt.model.aduan.CreateAduanResponse
import com.maluku.sma_rt.model.aduan.GetAduanByIDResponse
import com.maluku.sma_rt.model.aduan.GetAllAduanResponse
import com.maluku.sma_rt.model.dompetkeluarga.*
import com.maluku.sma_rt.model.dompetrt.DefaultDompetRTResponse
import com.maluku.sma_rt.model.dompetrt.GetDompetRTByLoginResponse
import com.maluku.sma_rt.model.informasi.*
import com.maluku.sma_rt.model.keluarga.*
import com.maluku.sma_rt.model.keranjang.*
import com.maluku.sma_rt.model.login.OnLoginSuccessResponse
import com.maluku.sma_rt.model.order.*
import com.maluku.sma_rt.model.password.DefaultPasswordResponse
import com.maluku.sma_rt.model.pengurus.CreatePengurusResponse
import com.maluku.sma_rt.model.pengurus.DefaultPengurusResponse
import com.maluku.sma_rt.model.pengurus.GetDataPengurusByLoginResponse
import com.maluku.sma_rt.model.persuratan.*
import com.maluku.sma_rt.model.produk.*
import com.maluku.sma_rt.model.tagihan.CreateTagihanResponse
import com.maluku.sma_rt.model.tagihan.GetAllTagihanResponse
import com.maluku.sma_rt.model.updateanddelete.OnDataResponse
import com.maluku.sma_rt.model.warga.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*
import retrofit2.http.GET

interface Service {

    @FormUrlEncoded
    @POST("warga")
    fun signUpWarga(
        @Field("kode_keluarga") kode_keluarga: String,
        @Field("gender") gender: String,
        @Field("no_hp") no_hp: String,
        @Field("nama") nama: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("token_firebase") token_firebase: String
    ): Call<CreateWargaResponse>

    @FormUrlEncoded
    @POST("warga/login")
    fun signInWarga(
         @Field("email") email: String,
         @Field("password") password: String,
         @Field("token_firebase") token_firebase: String
    ): Call<WargaLoginResponse>

    @Headers("Authorization: Bearer Token")
    @GET("/me")
    fun getMyData(@Field("token") token: String): Call<DetailLoginedWargaResponse>

    // Pengurus RT - Admin RT
    @FormUrlEncoded
    @POST("pengurus")
    fun signUpAdminRT(
        @Field("kode_rt") kode_rt: String,
        @Field("gender") genderAdmin: String,
        @Field("no_hp") noHpAdmin: String,
        @Field("nama") namaAdmin: String,
        @Field("email") emailAdmin: String,
        @Field("password") password: String,
        @Field("token_firebase") token_firebase: String
    ): Call<CreatePengurusResponse>

    @FormUrlEncoded
    @POST("pengurus/login")
    fun loginAdmin(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<OnLoginSuccessResponse>

    // Get Data Pengurus RT By Login
    @GET("pengurus/me")
    fun getDataLoginPengurus(
        @Header("Authorization") authHeader: String
    ): Call<GetDataPengurusByLoginResponse>

    // Update Pengurus
    @FormUrlEncoded
    @PUT("pengurus/{pengurus_id}")
    fun updatePengurus(
        @Header("Authorization") authHeader: String,
        @Path("pengurus_id") pengurus_id: String,
        @Field("gender") gender: String,
        @Field("no_hp") no_hp: String,
        @Field("nama") nama: String,
        @Field("email") email: String,
        @Field("gambar") gambar: String,
        @Field("kode_rt") kode_rt: String
    ): Call<DefaultPengurusResponse>

    // Warga
    // Get Data Warga
    @GET("warga")
    fun getListWarga(
        @Header("Authorization") authHeader: String,
        @Query("id_keluarga") idKeluarga: String?,
        @Query("nama") nama: String?
    ): Call<GetAllWargaResponse>

    // Get Warga By ID Keluarga
    @GET("warga")
    fun getWargaByIDKeluarga(
        @Header("Authorization") authHeader: String,
        @Query("id_keluarga") idKeluarga: String
    ): Call<GetAllWargaResponse>

    // Get Warga By ID Keluarga
    @GET("warga/me")
    fun getDataLoginWarga(
        @Header("Authorization") authHeader: String
    ): Call<WargaGetDataLoginResponse>

    // Update Warga
    @FormUrlEncoded
    @PUT("warga/{warga_id}")
    fun updateWarga(
        @Header("Authorization") authHeader: String,
        @Path("warga_id") warga_id: String,
        @Field("gender") gender: String,
        @Field("no_hp") no_hp: String,
        @Field("nama") nama: String,
        @Field("email") email: String,
        @Field("gambar") gambar: String,
    ): Call<UpdateWargaResponse>

    // Warga lupa password
    @FormUrlEncoded
    @POST("warga/forgetpassword")
    fun forgetPassword(
        @Field("email") email: String
    ): Call<DefaultWargaResponse>

    // Warga reset password
    @FormUrlEncoded
    @POST("warga/resetpasswordbykode")
    fun resetPassword(
        @Field("kode") kode: String,
        @Field("password") password: String
    ): Call<DefaultWargaResponse>

    // Tambah Keluarga
    @FormUrlEncoded
    @POST("keluarga")
    fun tambahKeluarga(
        @Header("Authorization") authHeader: String,
        @Field("nama") nama: String,
    ): Call<CreateKeluargaResponse>

    // Get Keluarga Saya
    @GET("keluargasaya")
    fun getKeluargaSaya(
        @Header("Authorization") authHeader: String
    ): Call<GetKeluargaSayaResponse>

    // Get Keluarga By ID
    @GET("keluarga/{id_keluarga}")
    fun getKeluargaById(
        @Header("Authorization") authHeader: String,
        @Path("id_keluarga") id_keluarga: String
    ): Call<GetKeluargaByIDResponse>

    // Update Toko
    @FormUrlEncoded
    @PUT("keluarga/{id_keluarga}")
    fun updateToko(
        @Header("Authorization") authHeader: String,
        @Path("id_keluarga") id_keluarga: String,
        @Field("nama_toko") nama_toko: String,
        @Field("gambar") gambar: String,
    ): Call<UpdateKeluargaResponse>

    // Get All Keluarga
    @GET("keluarga")
    fun getAllKeluarga(
        @Header("Authorization") authHeader: String,
        @Query("search") nama: String?
    ): Call<GetAllKeluargaResponse>

    // Get All Produk
    @GET("produk")
    fun getAllProduk(
        @Header("Authorization") authHeader: String,
        @Query("id_keluarga") idKeluarga: String?,
        @Query("nama") nama: String?
    ): Call<GetAllProdukResponse>

    // Get Produk By ID
    @GET("produk/{id_produk}")
    fun getProdukById(
        @Header("Authorization") authHeader: String,
        @Path("id_produk") idProduk: String
    ): Call<GetProductByIDResponse>


    // Get All Data Keluarga
    @GET("keluarga/warga")
    fun getListKeluarga(
        @Header("Authorization") authHeader: String,
    ): Call<GetListKeluargaResponse>

    // Get Produk Warga Dengan Token
    @GET("produk/keluarga")
    fun getAllProdukKeluarga(
        @Header("Authorization") authHeader: String
    ): Call<GetAllProdukKeluargaResponse>

    // Tambah Produk
    @FormUrlEncoded
    @POST("produk")
    fun createProduct(
        @Header("Authorization") authHeader: String,
        @Field("nama") nama: String,
        @Field("detail") detail: String,
        @Field("gambar") gambar: String,
        @Field("harga") harga: String,
    ): Call<CreateProductResponse>

    // Update Produk
    @FormUrlEncoded
    @PUT("produk/{product_id}")
    fun updateProductByID(
        @Header("Authorization") authHeader: String,
        @Path("product_id") product_id: String,
        @Field("nama") nama: String,
        @Field("detail") detail: String,
        @Field("gambar") gambar: String,
        @Field("harga") harga: String,
        @Field("tersedia") tersedia: String,
    ): Call<UpdateProductByIDResponse>

    // Hapus produk
    @DELETE("produk/{product_id}")
    fun deleteProductByID(
        @Header("Authorization") authHeader: String,
        @Path("product_id") product_id: String,
    ): Call<DeleteProductByIDResponse>

    // Tambah Tagihan
    @FormUrlEncoded
    @POST("tagihan")
    fun crateTagihan(
        @Header("Authorization") authHeader: String,
        @Field("nama") nama: String,
        @Field("detail") detail: String,
        @Field("jumlah") jumlah: String,
    ): Call<CreateTagihanResponse>

    //ambil seluruh data pada tagihan
    @GET("tagihan")
    fun getAllTagihan(
        @Header("Authorization") authHeader: String
    ): Call<GetAllTagihanResponse>

    //ambil seluruh data pada tagihan untuk pengurus RT
    @GET("tagihan")
    fun getAllTagihanRT(
        @Header("Authorization") authHeader: String
    ): Call<GetAllTagihanResponse>

    //bayar tagihan
    @PUT("tagihan/{id_tagihan}")
    fun bayarTagihan(
        @Header("Authorization") authHeader: String,
        @Path("id_tagihan") id_tagihan: String
    ): Call<OnDataResponse>

    //Aduan
    //Create
    // Tambah Tagihan
    @FormUrlEncoded
    @POST("aduan")
    fun createAduan(
        @Header("Authorization") authHeader: String,
        @Field("judul") judul: String,
        @Field("gambar") gambar: String,
        @Field("deskripsi") deskripsi: String,
    ): Call<CreateAduanResponse>

    //update
    @FormUrlEncoded
    @PUT("aduan/{token}")
    fun updateAduan(
        @Header("Authorization") authHeader: String,
        @Path("id") id: String,
        @Field("judul") judul: String,
        @Field("gambar") gambar: String,
        @Field("deskripsi") deskripsi: String,
    ): Call<OnDataResponse>

    //delete
    @DELETE("aduan/{id}")
    fun deleteAduan(
        @Header("Authorization") authHeader: String,
        @Path("id") id: String
    ): Call<OnDataResponse>

    //get data aduan by ID
    @GET("aduan/{id}")
    fun getAduanByID(
        @Header("Authorization") authHeader: String,
        @Path("id") id: String
    ): Call<GetAduanByIDResponse>

    //Get Data All Aduan
    @GET("aduan")
    fun getAllAduan(
        @Header("Authorization") authHeader: String
    ): Call<GetAllAduanResponse>

    //Get Data All Aduan 1 RT
    @GET("alladuan")
    fun getAllAduanRT(
        @Header("Authorization") authHeader: String
    ): Call<GetAllAduanResponse>

    //Terima Aduan
    @PUT("aduan/terima/{id_aduan}")
    fun terimaAduan(
        @Header("Authorization") authHeader: String,
        @Path("id_aduan") id_aduan: String
    ): Call<GetAduanByIDResponse>

    //Informasi
    //Create
    @FormUrlEncoded
    @POST("informasi")
    fun createInformasi(
        @Header("Authorization") authHeader: String,
        @Field("judul") judul: String,
        @Field("gambar") gambar: String,
        @Field("detail") detail: String,
        @Field("kategori") kategori: String,
        @Field("lokasi") lokasi: String
    ): Call<CreateInformasiResponse>

    //update
    @FormUrlEncoded
    @PUT("informasi/{id}")
    fun updateInformasi(
        @Header("Authorization") authHeader: String,
        @Path("id") id: String,
        @Field("judul") judul: String,
        @Field("gambar") gambar: String,
        @Field("detail") detail: String,
        @Field("kategori") kategori: String,
        @Field("lokasi") lokasi: String
    ): Call<UpdateInformasiResponse>

    //delete
    @DELETE("informasi/{id}")
    fun deleteInformasi(
        @Header("Authorization") authHeader: String,
        @Path("id") id: String
    ): Call<DeleteInformasiResponse>

    //get informasi by ID
    @GET("informasi/{id}")
    fun getInformasiByID(
        @Header("Authorization") authHeader: String,
        @Path("id") id: String
    ): Call<GetInformasiByIDResponse>

    //Get Data All Informasi
    @GET("informasi")
    fun getAllInformasi(
        @Header("Authorization") authHeader: String
    ): Call<GetAllInformasiResponse>

    //Get Data All Informasi
    @GET("infoterkini")
    fun getInfoTerkini(
        @Header("Authorization") authHeader: String
    ): Call<GetAllInformasiResponse>

    //Get Data All Informasi
    @GET("kegiatan")
    fun getKegiatan(
        @Header("Authorization") authHeader: String
    ): Call<GetAllInformasiResponse>

    // Dompet RT
    // Get Data Dompet RT berdasarkan Login
    @GET("dompetrt/me")
    fun getDompetRT(
        @Header("Authorization") authHeader: String
    ): Call<GetDompetRTByLoginResponse>

    // Withdraw Dompet RT
    @FormUrlEncoded
    @PUT("dompetrt/withdraw")
    fun withdrawDompetRT(
        @Header("Authorization") authHeader: String,
        @Field("jumlah") jumlah: String
    ): Call<DefaultDompetRTResponse>

    // Dompet Keluarga
    // Get All Dompet Keluarga
    @GET("dompetkeluarga")
    fun getAllDompetKeluarga(
        @Header("Authorization") authHeader: String
    ): Call<GetAllDompetKeluargaResponse>

    // Get Dompet Keluarga Dengan Login
    @GET("dompetkeluarga/me")
    fun getDompetKeluargaByLoginSession(
        @Header("Authorization") authHeader: String
    ): Call<GetDompetKeluargaByIdResponse>

    // Get Dompet Keluarga dengan Id Keluarga
    @GET("dompetkeluarga/{id_keluarga}")
    fun getDompetKeluargaById(
        @Header("Authorization") authHeader: String,
        @Path("id_keluarga") product_id: String
    ): Call<GetDompetKeluargaByIdResponse>

    // Top Up Dompet Keluarga
    @FormUrlEncoded
    @PUT("dompetkeluarga/topup")
    fun topUpDompetKeluarga(
        @Header("Authorization") authHeader: String,
        @Field("jumlah") jumlah: String
    ): Call<DefaultDompetKeluargaResponse>

    // Top Up Dompet Keluarga
    @FormUrlEncoded
    @PUT("dompetkeluarga/withdraw")
    fun withdrawDompetKeluarga(
        @Header("Authorization") authHeader: String,
        @Field("jumlah") jumlah: String
    ): Call<DefaultDompetKeluargaResponse>

    //Order
    // Create Order Pakai Saldo
    @POST("order/create/Saldo")
    fun createOrderPakaiSaldo(
        @Header("Authorization") authHeader: String,
        @Body order: ArrayList<KeranjangCheckout>
    ): Call<CreateOrderResponse>

    // Backup Create Order Pakai Saldo
    /*
    @FormUrlEncoded
    @POST("order/create/Saldo")
    fun createOrderPakaiSaldo(
        @Header("Authorization") authHeader: String,
        @Field("order") order: ArrayList<CreateOrderBody>
    ): Call<CreateOrderResponse>

     */

    // Create Order Pakai COD
    @POST("order/create/COD")
    fun createOrderPakaiCOD(
        @Header("Authorization") authHeader: String,
        @Body order: ArrayList<KeranjangCheckout>
    ): Call<CreateOrderResponse>

    // Backup Create Order Pakai COD
    /*
    @FormUrlEncoded
    @POST("order/create/COD")
    fun createOrderPakaiCOD(
        @Header("Authorization") authHeader: String,
        @Field("order") order: ArrayList<CreateOrderBody>
    ): Call<CreateOrderResponse>
    */

    // Get All Order
    @GET("order/warga")
    fun getAllOrder(
        @Header("Authorization") authHeader: String
    ): Call<GetAllOrderResponse>

    // Get All Toko Order
    @GET("order/toko")
    fun getAllTokoOrder(
        @Header("Authorization") authHeader: String,
        @Query("status") status: String?
    ): Call<GetAllOrderResponse>

    // Get Order By ID
    @GET("order/warga/{id_order}")
    fun getOrderByID(
        @Header("Authorization") authHeader: String,
        @Path("id_order") id_order: String
    ): Call<GetOrderByIDResponse>

    // Order Diproses
    @PUT("order/proses/{id_order}")
    fun prosesOrder(
        @Header("Authorization") authHeader: String,
        @Path("id_order") id_order: String
    ): Call<DefaultOrderResponse>

    // Order Dicancel
    @PUT("order/cancel/{id_order}")
    fun cancelOrder(
        @Header("Authorization") authHeader: String,
        @Path("id_order") id_order: String
    ): Call<DefaultOrderResponse>

    // Order Diproses
    @PUT("order/selesai/{id_order}")
    fun selesaiOrder(
        @Header("Authorization") authHeader: String,
        @Path("id_order") id_order: String
    ): Call<DefaultOrderResponse>

    // Keranjang
    // Get Keranjang
    @GET("cart")
    fun getKeranjang(
        @Header("Authorization") authHeader: String,
    ): Call<GetKeranjangResponse>

    // Tambah Produk Ke Keranjang
    @PUT("cart/add")
    fun tambahProdukKeKeranjang(
        @Header("Authorization") authHeader: String,
        @Body item: CreateOrderBody
    ): Call<KeranjangDefaultResponse>

    // Update Keranjang
    @PUT("cart")
    fun updateKeranjang(
        @Header("Authorization") authHeader: String,
        @Body item: ArrayList<CreateOrderBody>
    ): Call<KeranjangDefaultResponse>

    // Tambah Jumlah Produk
    @PUT("item/add/{idProduk}")
    fun tambahJumlahProduk(
        @Header("Authorization") authHeader: String,
        @Path("idProduk") idProduk: String
    ): Call<KeranjangDefaultResponse>

    // Kurang Jumlah Produk
    @PUT("item/min/{idProduk}")
    fun kurangJumlahProduk(
        @Header("Authorization") authHeader: String,
        @Path("idProduk") idProduk: String
    ): Call<KeranjangDefaultResponse>

    // Hapus Produk
    @PUT("item/del/{idProduk}")
    fun hapusProdukKeranjang(
        @Header("Authorization") authHeader: String,
        @Path("idProduk") idProduk: String
    ): Call<KeranjangDefaultResponse>

    // Password
    // Pengurus RT
    @FormUrlEncoded
    @PUT("pengurus/changepassword")
    fun changePassAdmin(
        @Header("Authorization") authHeader: String,
        @Field("password") password: String,
        @Field("new_password") new_password: String
    ): Call<DefaultPasswordResponse>

    // Lupa Password Pengurus RT
    @FormUrlEncoded
    @POST("pengurus/forgetpassword")
    fun forgetPassAdmin(
        @Header("Authorization") authHeader: String,
        @Field("email") email: String,
    ): Call<DefaultPasswordResponse>

    // Reset Password Pengurus RT
    @FormUrlEncoded
    @POST("pengurus/resetpasswordbykode")
    fun resetPassAdmin(
        @Header("Authorization") authHeader: String,
        @Field("kode") kode: String,
        @Field("password") password: String
    ): Call<DefaultPasswordResponse>

    // Warga
    @FormUrlEncoded
    @PUT("warga/changepassword")
    fun changePasswordWarga(
        @Header("Authorization") authHeader: String,
        @Field("password") password: String,
        @Field("new_password") new_password: String
    ): Call<DefaultPasswordResponse>

    /*
        Persuratan
     */
    //warga
    //create
    @FormUrlEncoded
    @POST("persuratan")
    fun createWargaPersuratan(
        @Header("Authorization") authHeader: String,
        @Field("judul") judul: String,
        @Field("penerima") penerima: String,
        @Field("tanggal") tanggal: String,
        @Field("keperluan") keperluan: String
    ): Call<CreatePersuratanResponse>

    //update
    @FormUrlEncoded
    @PUT("persuratan/{id_surat}")
    fun updateWargaPersuratan(
        @Header("Authorization") authHeader: String,
        @Path("id_surat") id_surat: String,
        @Field("judul") judul: String,
        @Field("penerima") penerima: String,
        @Field("tanggal") tanggal: String,
        @Field("keperluan") keperluan: String
    ): Call<OnDataResponse>

    //delete
    @DELETE("persuratan/{id_surat}")
    fun deleteSuratByID(
        @Header("Authorization") authHeader: String,
        @Path("id_surat") id_surat: String
    ): Call<OnDataResponse>

    //get all
    @GET("persuratan")
    fun getAllPersuratanData(
        @Header("Authorization") authHeader: String
    ): Call<GetAllPersuratanResponse>

    /*
    get all data dengan dikelompokan berdasarkan status
    0=Tolak, 1=Terkirim, 2=Di Proses, 3=Selesai
    */
    @GET("persuratan")
    fun getAllPersuratanDataCategorically(
        @Header("Authorization") authHeader: String,
        @Query("status") status: String
    ): Call<GetAllPersuratanResponse>

    @GET("persuratan/{id_surat}")
    fun getPersuratanDataByID(
        @Header("Authorization") authHeader: String,
        @Path("id_surat") id_surat: String
    ): Call<GetPersuratanByIDResponse>

    // Surat Diterima
    @FormUrlEncoded
    @PUT("persuratan/selesai/{id_surat}")
    fun terimaSurat(
        @Header("Authorization") authHeader: String,
        @Path("id_surat") id_surat: String,
        @Field("link") link: String
    ): Call<PersuratanDefaultResponse>

    // Surat Ditolak
    @FormUrlEncoded
    @PUT("persuratan/tolak/{id_surat}")
    fun tolakSurat(
        @Header("Authorization") authHeader: String,
        @Path("id_surat") id_surat: String,
        @Field("link") alasan: String
    ): Call<PersuratanDefaultResponse>
}