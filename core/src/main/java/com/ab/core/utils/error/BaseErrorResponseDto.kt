package com.ab.core.utils.error

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class BaseErrorResponseDto(
    @SerializedName("cod")
    val code: String?,
    val message: String
)
