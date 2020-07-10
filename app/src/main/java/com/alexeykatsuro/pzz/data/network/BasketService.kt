package com.alexeykatsuro.pzz.data.network

import com.alexeykatsuro.pzz.data.dto.basket.BasketResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface BasketService {
    @FormUrlEncoded
    @POST("api/v1/basket/add-item")
    suspend fun addItem(
        @Field("id") id: Long,
        @Field("type") type: String,
        @Field("size") size: String
    ): BasketResponse

    @FormUrlEncoded
    @POST("api/v1/basket/remove-item")
    suspend fun removeItem(
        @Field("id") id: Long,
        @Field("type") type: String,
        @Field("size") size: String
    ): BasketResponse
}