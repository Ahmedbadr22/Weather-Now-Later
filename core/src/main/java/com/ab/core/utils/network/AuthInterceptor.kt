package com.ab.core.utils.network

import com.ab.core.BuildConfig.APP_KEY
import com.ab.core.utils.constants.Endpoint.API_KEY_QUERY
import okhttp3.Interceptor
import okhttp3.Response
import retrofit2.Invocation


class AuthInterceptor: Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val invocation = request.tag(Invocation::class.java)

        val requiresAuth = invocation?.method()?.annotations?.any {
            it.annotationClass == AuthenticateWithAppId::class
        } ?: false

        if (requiresAuth) {
            val newUrlBuilder = request
                .url
                .newBuilder()
                .addQueryParameter(API_KEY_QUERY, APP_KEY)

            val newRequest = request
                .newBuilder()
                .url(newUrlBuilder.build())
                .build()

            return chain.proceed(newRequest)
        }

        return chain.proceed(request)
    }
}
