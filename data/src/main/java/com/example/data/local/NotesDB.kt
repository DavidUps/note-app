package com.example.data.local

import com.example.data.models.NoteEntity

interface NotesDB {

    fun getNotes(): List<NoteEntity>?
    fun insertNote(note: NoteEntity)
    fun updateNote(note: NoteEntity)
    fun deleteNote(note: NoteEntity)
}
