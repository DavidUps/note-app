package com.example.core.extensions

fun Boolean?.orEmpty(): Boolean = this ?: false
