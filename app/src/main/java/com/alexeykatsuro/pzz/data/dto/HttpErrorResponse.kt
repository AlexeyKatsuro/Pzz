package com.alexeykatsuro.pzz.data.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class HttpErrorResponse(

    @Json(name = "message")
    val message: String? = null,

    @Json(name = "error")
    val error: String? = null

) {
    val errorMessage: String = message ?: error ?: "No message"
}