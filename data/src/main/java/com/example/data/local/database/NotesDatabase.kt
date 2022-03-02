package com.example.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.local.database.dao.NotesDAO
import com.example.data.models.NoteEntity

@Database(entities = [NoteEntity::class], version = 1)
abstract class NotesDatabase : RoomDatabase() {

    abstract fun notesDao(): NotesDAO
}
