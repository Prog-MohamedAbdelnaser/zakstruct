package com.base.extensions

import android.content.Intent

fun Intent.clearActivityStack(): Intent {
    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    return this
}

fun Intent.hasData(): Boolean = this.data != null