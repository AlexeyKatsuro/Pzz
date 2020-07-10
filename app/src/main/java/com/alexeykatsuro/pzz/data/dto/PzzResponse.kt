package com.alexeykatsuro.pzz.data.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlin.reflect.KClass

@JsonClass(generateAdapter = true)
data class PzzResponse<T>(
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
        val data: Any? = null,
        @Json(name = "meta")
        val meta: Any? = null
    )
}