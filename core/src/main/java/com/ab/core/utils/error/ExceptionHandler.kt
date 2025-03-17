package com.ab.core.utils.error

import android.util.Log
import com.ab.core.R
import com.ab.core.utils.ext.ifNull
import com.ab.core.utils.ext.orNA
import com.ab.core.utils.resource.ResourceProviderImpl
import com.google.gson.Gson
import retrofit2.HttpException
import java.io.IOException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException
import kotlin.coroutines.cancellation.CancellationException

class ExceptionHandler(
    private val gson: Gson,
    private val resourceProviderImpl: ResourceProviderImpl,
) {
    fun handleAsFailure(throwable: Throwable): Failure {
        Log.i("AHMED_ERROR", "handleAsFailure: $throwable")
        // Propagate coroutine cancellations immediately.
        // this rethrow happen to cancel any collecting happen on the flow
        if (throwable is CancellationException) throw throwable

        val throwableMsg = throwable.message ?: throwable.localizedMessage

        val failure = when (throwable) {
            is HttpException -> {
                val statusCode = throwable.code()
                // Safely parse the error body to extract a meaningful message.
                val errorMessage = runCatching {
                    throwable.response()?.errorBody()?.string()?.let { errorBodyString ->
                        gson.fromJson(errorBodyString, BaseErrorResponseDto::class.java)?.message
                    }
                }.getOrElse { throwableMsg }

                when (statusCode) {
                    400 -> Failure.BadRequest(
                        resourceProviderImpl.getString(
                            R.string.bad_request_message,
                            "(${errorMessage.ifNull { statusCode.toString() }})"
                        ),
                        cause = throwable
                    )

                    401 -> Failure.UnAuthorized(
                        resourceProviderImpl.getString(
                            R.string.un_authorized_error,
                            "(${errorMessage.ifNull { statusCode.toString() }})"
                        ),
                        cause = throwable
                    )

                    404 -> Failure.NotFound(
                        resourceProviderImpl.getString(
                            R.string.not_found_network_error_msg,
                            "(${errorMessage.ifNull { statusCode.toString() }})"
                        ),
                        cause = throwable
                    )

                    500, 501, 502 -> Failure.InternalServer(
                        resourceProviderImpl.getString(
                            R.string.server_error,
                            "(${errorMessage.ifNull { statusCode.toString() }})"
                        ),
                        cause = throwable
                    )

                    else -> Failure.Unknown(
                        resourceProviderImpl.getString(
                            R.string.unknown_error,
                            "(${errorMessage.ifNull { statusCode.toString() }})"
                        ),
                        cause = throwable
                    )
                }
            }
            is UnknownHostException -> Failure.UnknownHost(
                resourceProviderImpl.getString(
                    R.string.unknown_host_error,
                    "(${throwableMsg.orNA()})"
                ),
                cause = throwable
            )

            is IOException -> Failure.IO(
                resourceProviderImpl.getString(
                    R.string.io_error_message_with_details,
                    "(${throwableMsg.orNA()})"
                ),
                cause = throwable
            )

            is TimeoutException -> Failure.Timeout(
                resourceProviderImpl.getString(
                    R.string.timeout_error,
                    "(${throwableMsg.orNA()})"
                ),
                cause = throwable
            )

            else -> Failure.Unknown(
                resourceProviderImpl.getString(
                    R.string.unknown_error,
                    "(${throwableMsg.orNA()})"
                ),
                cause = throwable
            )
        }
        return failure
    }
}
