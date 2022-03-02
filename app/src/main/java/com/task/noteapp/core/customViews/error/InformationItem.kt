package com.task.noteapp.core.customViews.error

import android.graphics.drawable.Drawable
import android.text.Spanned

data class InformationItem(
    var icon: Drawable?,
    var title: String,
    var message: String?,
    var messageSpanned: Spanned?,
    var actionOne: String?,
    var actionTwo: String?,
    var type: Type
) {

    companion object {
        enum class Type {
            INFORMATION,
            ONE_ACTION_PRIMARY_BUTTON,
            ONE_ACTION_SECONDARY_BUTTON,
            TWO_ACTIONS
        }
    }
}
