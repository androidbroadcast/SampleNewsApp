package dev.androidbroadcast.newsapi.utils

import okhttp3.Interceptor
import okhttp3.Response

internal class NewsApiKeyInterceptor(private val apikey: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request =
            chain.request().newBuilder()
                .url(
                    chain.request().url.newBuilder()
                        .addQueryParameter("apiKey", apikey)
                        .build()
                )
                .build()
        return chain.proceed(request)
    }
}
