package com.example.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.core.extensions.empty

@Entity
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    @ColumnInfo(name = "title")
    var title: String?,
    @ColumnInfo(name = "message")
    var message: String?,
    @ColumnInfo(name = "image")
    var image: String?,
    @ColumnInfo(name = "creation")
    var creationDate: String?,
    @ColumnInfo(name = "edited")
    var edited: Boolean = false
) {
    companion object {
        fun empty() =
            NoteEntity(Int.empty(), String.empty(), String.empty(), String.empty(), String.empty())
    }
}
