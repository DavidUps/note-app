package com.example.data.local.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.core.utils.BaseDao
import com.example.data.models.NoteEntity

@Dao
interface NotesDAO : BaseDao<NoteEntity> {

    @Query("SELECT * FROM NoteEntity")
    fun getNotes(): List<NoteEntity>?
}
