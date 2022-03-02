package com.example.core.utils

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Update

interface BaseDao<T> {

    @Insert
    fun insert(obj: T)

    @Insert
    fun insert(vararg obj: T)

    @Insert(onConflict = REPLACE)
    fun insertAll(obj: List<T>)

    @Update(onConflict = REPLACE)
    fun update(vararg obj: T)

    @Delete
    fun delete(obj: T)
}
