package com.example.core.extensions

fun Double.Companion.empty() = 0.0

fun Double?.orEmpty(): Double = this ?: 0.0
