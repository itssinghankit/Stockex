package com.itssinghankit.stockex.data.remote

import com.itssinghankit.stockex.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber
import javax.inject.Inject

class StockexInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val requestBuilder = request.newBuilder()
        Timber.d("intercepted")

        if (request.headers["AddRapidHeader"] == "true") {
            requestBuilder.addHeader("x-rapidapi-key", BuildConfig.RAPID_API_KEY)
            requestBuilder.addHeader("x-rapidapi-host", BuildConfig.RAPID_API_HOST)
        }
        requestBuilder.removeHeader("AddRapidHeader")
        return chain.proceed(requestBuilder.build())
    }
}