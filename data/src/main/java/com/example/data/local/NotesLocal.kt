package com.example.data.local

import com.example.data.local.database.NotesDatabase
import com.example.data.models.NoteEntity
import javax.inject.Inject

class NotesLocal @Inject constructor(database: NotesDatabase) : NotesDB {

    private val notesDao by lazy { database.notesDao() }

    override fun getNotes() = notesDao.getNotes()

    override fun insertNote(note: NoteEntity) = notesDao.insert(note)

    override fun updateNote(note: NoteEntity) = notesDao.update(note)

    override fun deleteNote(note: NoteEntity) = notesDao.delete(note)
}
