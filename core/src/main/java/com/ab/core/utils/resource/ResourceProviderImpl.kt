package com.ab.core.utils.resource

import android.content.Context
import androidx.annotation.StringRes

interface ResourceProvider {
    fun getString(@StringRes resId: Int, vararg formatArgs: Any): String
}

class ResourceProviderImpl(
    private val context: Context
): ResourceProvider {
    override fun getString(@StringRes resId: Int, vararg formatArgs: Any): String {
        return context.getString(resId, *formatArgs)
    }
}