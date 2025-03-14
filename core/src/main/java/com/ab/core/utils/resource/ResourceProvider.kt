package com.ab.core.utils.resource

import android.content.Context
import androidx.annotation.StringRes


class ResourceProvider(
    private val context: Context
) {
    fun getString(@StringRes resId: Int, vararg formatArgs: Any): String {
        return context.getString(resId, *formatArgs)
    }
}