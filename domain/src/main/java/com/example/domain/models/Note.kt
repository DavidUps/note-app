package com.example.domain.models

import com.example.core.extensions.empty
import com.example.data.models.NoteEntity
import java.util.Date

data class Note(
    val id: Int?,
    var title: String?,
    var message: String?,
    var image: String?,
    var creationDate: String?,
    var edited: Boolean = false
) {
    companion object {
        fun empty() =
            Note(Int.empty(), String.empty(), String.empty(), String.empty(), Date().toString(), true)
    }
}

fun Note.toData() = NoteEntity(id, title, message, image, creationDate.orEmpty(), edited)
fun NoteEntity.toDomain() = Note(id, title, message, image, creationDate, edited)
