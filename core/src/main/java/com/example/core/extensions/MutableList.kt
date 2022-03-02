package com.example.core.extensions

fun <T> MutableList<T>?.orEmpty(): MutableList<T> = this ?: mutableListOf()
