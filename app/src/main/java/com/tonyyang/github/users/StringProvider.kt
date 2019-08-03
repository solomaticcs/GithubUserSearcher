package com.tonyyang.github.users

import android.content.Context

class StringProvider(private val context: Context) {
    fun getString(resId: Int, vararg formatArgs: Any): String = this.context.getString(resId, *formatArgs)
}