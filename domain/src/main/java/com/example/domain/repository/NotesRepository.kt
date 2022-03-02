package com.example.domain.repository

import com.example.core.exception.Failure
import com.example.core.functional.Either
import com.example.domain.models.Action
import com.example.domain.models.Note

interface NotesRepository {

    suspend fun getNotes(): Either<Failure, List<Note>>
    suspend fun modifyNote(note: Note, action: Action): Either<Failure, List<Note>>
}
