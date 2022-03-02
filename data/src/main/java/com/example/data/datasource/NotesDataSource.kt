package com.example.data.datasource

import com.example.core.exception.Failure
import com.example.core.functional.Either
import com.example.data.models.ActionEntity
import com.example.data.models.NoteEntity

interface NotesDataSource {

    suspend fun getNotes(): Either<Failure, List<NoteEntity>>
    suspend fun modifyNote(
        note: NoteEntity,
        action: ActionEntity
    ): Either<Failure, List<NoteEntity>>
}
