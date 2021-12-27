package com.maluku.sma_rt.api

import com.maluku.sma_rt.model.aduan.CreateAduanResponse
import com.maluku.sma_rt.model.aduan.GetAduanByIDResponse
import com.maluku.sma_rt.model.aduan.GetAllAduanResponse
import com.maluku.sma_rt.model.dompetkeluarga.*
import com.maluku.sma_rt.model.dompetrt.GetDompetRTByLoginResponse
import com.maluku.sma_rt.model.informasi.*
import com.maluku.sma_rt.model.keluarga.*
import com.maluku.sma_rt.model.login.OnLoginSuccessResponse
import com.maluku.sma_rt.model.order.CreateOrderBody
import com.maluku.sma_rt.model.order.CreateOrderResponse
import com.maluku.sma_rt.model.pengurus.CreatePengurusResponse
import com.maluku.sma_rt.model.produk.CreateProductResponse
import com.maluku.sma_rt.model.produk.DeleteProductByIDResponse
import com.maluku.sma_rt.model.produk.GetAllProdukResponse
import com.maluku.sma_rt.model.produk.UpdateProductByIDResponse
import com.maluku.sma_rt.model.tagihan.CreateTagihanResponse
import com.maluku.sma_rt.model.tagihan.GetAllTagihanResponse
import com.maluku.sma_rt.model.updateanddelete.OnDataResponse
import com.maluku.sma_rt.model.warga.*
import retrofit2.Call
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
        @Field("password") password: String
    ): Call<CreateWargaResponse>

    @FormUrlEncoded
    @POST("warga/login")
    fun signInWarga(
         @Field("email") email: String,
         @Field("password") password: String,
    ): Call<WargaLoginResponse>

    @Headers("Authorization: Bearer Token")
    @GET("/me")
    fun getMyData(@Field("token") token: String): Call<DetailLoginedWargaResponse>

    @FormUrlEncoded
    @POST("pengurus")
    fun signUpAdminRT(
        @Field("kode_rt") kode_rt: String,
        @Field("gender") genderAdmin: String,
        @Field("no_hp") noHpAdmin: String,
        @Field("nama") namaAdmin: String,
        @Field("email") emailAdmin: String,
        @Field("password") password: String
    ): Call<CreatePengurusResponse>

    @FormUrlEncoded
    @POST("pengurus/login")
    fun loginAdmin(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<OnLoginSuccessResponse>

    // Get Data Warga
    @GET("warga")
    fun getListWarga(
        @Header("Authorization") authHeader: String
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

    // Update Toko
    @FormUrlEncoded
    @PUT("keluarga/{id_keluarga}")
    fun updateToko(
        @Header("Authorization") authHeader: String,
        @Path("id_keluarga") product_id: String,
        @Field("nama_toko") nama_toko: String,
        @Field("gambar") gambar: String,
    ): Call<UpdateKeluargaResponse>

    // Get All Keluarga
    @GET("keluarga")
    fun getAllKeluarga(
        @Header("Authorization") authHeader: String,
    ): Call<GetAllKeluargaResponse>

    // Get All Produk
    @GET("produk")
    fun getAllProduk(
        @Header("Authorization") authHeader: String
    ): Call<GetAllProdukResponse>


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
        @Header("Authorization") authHeader: String,
        @Field("id_keluarga") idKeluarga: String
    ): Call<GetAllTagihanResponse>

    //ambil seluruh data pada tagihan untuk pengurus RT
    @GET("tagihan")
    fun getAllTagihanRT(
        @Header("Authorization") authHeader: String
    ): Call<GetAllTagihanResponse>

    //bayar tagihan
    @FormUrlEncoded
    @PUT("tagihan/{token}")
    fun bayarTagihan(
        @Header("Authorization") authHeader: String,
        @Path("token") token: String
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
    @FormUrlEncoded
    @DELETE("aduan/{id}")
    fun deleteAduan(
        @Header("Authorization") authHeader: String,
        @Path("id") id: String
    ): Call<OnDataResponse>

    //get data aduan by ID
    @GET("aduan/{id}")
    fun getAduanByID(
        @Header("Authorization") authHeader: String,
        @Path("token") token: String
    ): Call<GetAduanByIDResponse>

    //Get Data All Aduan
    @GET("aduan")
    fun getAllAduan(
        @Header("Authorization") authHeader: String
    ): Call<GetAllAduanResponse>

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
    @PUT("informasi/{token}")
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
    @FormUrlEncoded
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

    // DompetRT
    //Get Data All Informasi
    @GET("dompetrt/me")
    fun getDompetRT(
        @Header("Authorization") authHeader: String
    ): Call<GetDompetRTByLoginResponse>

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
    @PUT("dompetkeluarga/{id_keluarga}")
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
    // Create Order
    @FormUrlEncoded
    @POST("order")
    fun createOrder(
        @Header("Authorization") authHeader: String,
        @Field("order") order: ArrayList<CreateOrderBody>
    ): Call<CreateOrderResponse>
}