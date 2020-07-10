package com.alexeykatsuro.pzz.data.dto.basket


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BasketResponse(
    @Json(name = "error")
    val error: Boolean? = null,
    @Json(name = "code")
    val code: Int? = null,
    @Json(name = "response")
    val response: Response? = null
) {
    @JsonClass(generateAdapter = true)
    data class Response(
        @Json(name = "data")
        val data: BasketDataResponse? = null,
        @Json(name = "meta")
        val meta: Any? = null
    )
}