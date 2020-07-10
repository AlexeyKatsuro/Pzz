package com.alexeykatsuro.pzz.data.network

import okhttp3.Interceptor
import okhttp3.Response

class AcceptInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val modifiedRequest = request
            .newBuilder()
            .header("Accept", "application/json")
/*            .header("Cache-Control", "no-cache")
            .header("Host", "pzz.by")
            .header("User-Agent", "PostmanRuntime/7.25.0")
            .header("Accept-Encoding", "gzip, deflate, br")
            .header("Connection", "keep-alive")
            .header("Accept-Encoding", " gzip, deflate, br")
            .header("Accept-Language", " ru-BY,ru;q=0.9,en-US;q=0.8,en;q=0.7,ru-RU;q=0.6")
            .header("Connection", " keep-alive")
            .header(
                "Cookie",
                " _ga=GA1.2.2113252708.1588579116; _ym_d=1588579116; _ym_uid=1588579116245379271; PHPSESSID=05f0e8bb157022ef7d9cc8f746482a7b; _gid=GA1.2.1556394338.1591371152; _ym_isad=1; _ym_visorc_23913451=w; _gat=1"
            )*/
            .header("Host", "pzz.by")
            .build()
        return chain.proceed(modifiedRequest)
    }
}