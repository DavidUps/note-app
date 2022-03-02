package com.task.noteapp.core.extensions

import android.graphics.drawable.Drawable
import com.task.noteapp.core.customViews.error.ErrorView
import com.task.noteapp.core.customViews.error.InformationItem.Companion.Type

fun ErrorView.show(type: Type, title: String, message: String, actionOne: String) {
    this.visible()
    this.bringToFront()
    this.init(type, title, message, actionOne)
}

fun ErrorView.show(icon: Drawable?, type: Type, title: String, message: String, actionOne: String) {
    this.visible()
    this.bringToFront()
    this.init(icon, type, title, message, actionOne)
}

fun ErrorView.show(
    type: Type,
    title: String,
    message: String,
    actionOne: String,
    actionTwo: String
) {
    this.visible()
    this.bringToFront()
    this.init(type, title, message, actionOne, actionTwo)
}

fun ErrorView.close() {
    this.gone()
}
