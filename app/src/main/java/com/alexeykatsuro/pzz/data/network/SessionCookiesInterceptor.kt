package com.alexeykatsuro.pzz.data.network

import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber

class SessionCookiesInterceptor() : Interceptor {

    companion object {
        const val PHPSESSID = "PHPSESSID"
        const val COOKIE = "Cookie"
        const val SET_COOKIE = "Set-Cookie"
    }

    // TODO move to persistence storage like SessionBearer
    private var phpSessionCookie: String? = null

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalResponse: Response
        val sessionCookie = phpSessionCookie

        if (sessionCookie.isNullOrBlank()) {
            originalResponse = chain.proceed(chain.request())

            if (originalResponse.headers(SET_COOKIE).isNotEmpty()) {
                for (header in originalResponse.headers(SET_COOKIE)) {
                    val phpSession = header.split(';').find {
                        it.contains(PHPSESSID)
                    }
                    if (phpSession != null) {
                        phpSessionCookie = phpSession
                    }
                    Timber.e("phpSessionCookie $phpSessionCookie")
                }
            }
        } else {
            val request = chain.request()
            val modifiedRequest = request
                .newBuilder()
                .header(COOKIE, sessionCookie)
                .build()
            originalResponse = chain.proceed(modifiedRequest)
        }

        return originalResponse
    }

}
