package com.example.domain.models

import com.example.data.models.ActionEntity

enum class Action {
    INSERT,
    UPDATE,
    DELETE
}

fun Action.toData() = when (this) {
    Action.INSERT -> ActionEntity.INSERT
    Action.UPDATE -> ActionEntity.UPDATE
    Action.DELETE -> ActionEntity.DELETE
}
