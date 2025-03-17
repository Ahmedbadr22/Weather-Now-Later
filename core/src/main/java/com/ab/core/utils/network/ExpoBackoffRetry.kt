package com.ab.core.utils.network

import com.ab.core.utils.constants.AppConst.MAX_RETRY_ATTEMPTS
import kotlinx.coroutines.delay
import kotlin.math.pow


/**
 * Executes the given action with an exponential backoff retry strategy.
 *
 * This function attempts to execute the provided suspending action up to a maximum number of retries.
 * If the action fails, it will wait for an exponentially increasing delay before retrying.
 * If all retry attempts are exhausted, the last encountered exception is thrown.
 *
 * @param maxRetries The maximum number of retry attempts before giving up. Defaults to MAX_RETRY_ATTEMPTS.
 * @param action The suspending function to execute with retry logic.
 *
 * @throws Throwable If the action fails after the maximum number of retries.
 */
suspend fun expoBackoffRetry(
    maxRetries: Int = MAX_RETRY_ATTEMPTS, // Maximum number of retry attempts before failure
    action: suspend () -> Unit // The operation to execute with retry logic
) {
    var attempt = 0 // Track the current retry attempt

    while (attempt < maxRetries) {
        try {
            action() // Execute the action
            return // If successful, exit the function
        } catch (exception: Throwable) {
            attempt++ // Increment the retry attempt count

            // If max retries reached, rethrow the exception
            if (attempt >= maxRetries) {
                throw exception
            } else {
                // Calculate exponential backoff delay (2^attempt * 1000ms)
                val backoffDelay = 1000L * 2.0.pow(attempt).toLong()

                // Suspend execution for the computed backoff duration before retrying
                delay(backoffDelay)
            }
        }
    }
}
