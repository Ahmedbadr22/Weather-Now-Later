package com.ab.core.utils.error

sealed class Failure(
    open val message: String,
    open val cause: Throwable
) {
    data class IO(
        override val message: String,
        override val cause: Throwable
    ): Failure(message, cause)

    data class NotFound(
        override val message: String,
        override val cause: Throwable
    ): Failure(message, cause)

    data class UnAuthorized(
        override val message: String,
        override val cause: Throwable
    ): Failure(message, cause)

    data class BadRequest(
        override val message: String,
        override val cause: Throwable
    ): Failure(message, cause)

    data class InternalServer(
        override val message: String,
        override val cause: Throwable
    ): Failure(message, cause)

    data class JsonSyntax(
        override val message: String,
        override val cause: Throwable
    ): Failure(message, cause)

    data class NoDataFound(
        override val message: String,
        override val cause: Throwable
    ): Failure(message, cause)

    data class Timeout(
        override val message: String,
        override val cause: Throwable
    ): Failure(message, cause)

    data class NoInternet(
        override val message: String,
        override val cause: Throwable
    ): Failure(message, cause)

    data class InValidCredentials(
        override val message: String,
        override val cause: Throwable
    ): Failure(message, cause)

    data class Unknown(
        override val message: String,
        override val cause: Throwable
    ): Failure(message, cause)

    data class UnknownHost(
        override val message: String,
        override val cause: Throwable
    ): Failure(message, cause)
}