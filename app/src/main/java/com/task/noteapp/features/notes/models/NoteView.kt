package com.task.noteapp.features.notes.models

import android.os.Parcelable
import com.example.core.extensions.orEmpty
import com.example.domain.models.Note
import kotlinx.parcelize.Parcelize
import java.text.SimpleDateFormat
import java.util.Date

@Parcelize
data class NoteView(
    val id: Int? = null,
    var title: String,
    var message: String,
    var image: String,
    var creationDate: String = getDate(),
    var edited: Boolean = false
) : Parcelable {
    companion object {
        fun empty() = NoteView(0, "", "", "", "", false)
    }
}

fun Note.toView() = NoteView(
    id.orEmpty(),
    title.orEmpty(),
    message.orEmpty(),
    image.orEmpty(),
    creationDate.orEmpty(),
    edited
)

fun NoteView.toDomain() = Note(id, title, message, image, creationDate)

fun getDate(): String {
    val sdf = SimpleDateFormat("dd/M/yyyy")
    return sdf.format(Date())
}
