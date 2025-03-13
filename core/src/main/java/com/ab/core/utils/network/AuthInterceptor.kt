package com.ab.core.utils.network
//
//import android.util.Log
//import com.jrm.core.network.Authenticated
//import com.jrm.data.source.local.datastore.AccountDataStore
//import kotlinx.coroutines.runBlocking
//import okhttp3.HttpUrl
//import okhttp3.Interceptor
//import okhttp3.Response
//import retrofit2.Invocation
//
//class AuthInterceptor(
//    private val accountDataStore: AccountDataStore,
//    private val apiKey: String // API key to be added as a query parameter
//) : Interceptor {
//
//    override fun intercept(chain: Interceptor.Chain): Response {
//        val request = chain.request()
//
//        // Retrieve the Invocation tag which contains method details.
//        val invocation = request.tag(Invocation::class.java)
//        val requiresAuth = invocation?.method()?.annotations?.any {
//            it.annotationClass == Authenticated::class
//        } ?: false
//
//        // Start building a new request with the API key as a query parameter
//        val newUrlBuilder = request.url.newBuilder()
//            .addQueryParameter("appid", apiKey) // Append API key to query
//
//        if (requiresAuth) {
//            val userCredentials = runBlocking { accountDataStore.getUserCredentialsDto() }
//            Log.i("Token", "Appending API key: $apiKey, Token: $userCredentials")
//
//            if (userCredentials != null) {
//                // If authentication is required, modify request with Bearer token
//                val newRequest = request.newBuilder()
//                    .url(newUrlBuilder.build()) // Use modified URL with API key
//                    .addHeader("Authorization", "Bearer ${userCredentials.token}")
//                    .build()
//                return chain.proceed(newRequest)
//            }
//        }
//
//        // Proceed with modified URL but without Authorization header if not required
//        val newRequest = request.newBuilder()
//            .url(newUrlBuilder.build())
//            .build()
//
//        return chain.proceed(newRequest)
//    }
//}
