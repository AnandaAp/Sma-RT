package com.maluku.sma_rt.model.produk

import com.google.gson.annotations.SerializedName

data class DeleteProductByIDResponse(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("message")
	val message: String? = null
)
