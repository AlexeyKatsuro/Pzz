package com.alexeykatsuro.pzz.data.mappers.error

import com.alexeykatsuro.pzz.data.dto.HttpErrorResponse
import com.alexeykatsuro.pzz.data.mappers.Mapper
import com.alexeykatsuro.pzz.utils.extensions.errorMessage
import com.alexeykatsuro.pzz.utils.extensions.errorResponse
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HttpErrorMapper @Inject constructor(moshi: Moshi) :
    Mapper<HttpException, String> {

    private val errorAdapter: JsonAdapter<HttpErrorResponse> =
        moshi.adapter(HttpErrorResponse::class.java)

    override fun map(from: HttpException): String {
        return kotlin.runCatching {
            errorAdapter.fromJson(from.errorResponse!!)!!.errorMessage
        }.getOrElse {
            from.errorMessage
        }
    }
}