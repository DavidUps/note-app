package com.example.domain.repository

import com.example.core.exception.Failure
import com.example.core.functional.Either
import com.example.data.datasource.NotesDataSource
import com.example.domain.models.Action
import com.example.domain.models.Note
import com.example.domain.models.toData
import com.example.domain.models.toDomain
import javax.inject.Inject

class NotesRepositoryImp @Inject constructor(private val dataSource: NotesDataSource) :
    NotesRepository {

    override suspend fun getNotes(): Either<Failure, List<Note>> =
        runCatching {
            dataSource.getNotes()
        }.map { either ->
            when (either) {
                is Either.Right -> Either.Right(success = either.success.map { it.toDomain() })
                is Either.Left -> Either.Left(error = either.error)
            }
        }.getOrElse { Either.Left(error = Failure.Throwable(it)) }

    override suspend fun modifyNote(note: Note, action: Action): Either<Failure, List<Note>> =
        runCatching {
            dataSource.modifyNote(note = note.toData(), action = action.toData())
        }.map { either ->
            when (either) {
                is Either.Right -> Either.Right(success = either.success.map { it.toDomain() })
                is Either.Left -> Either.Left(error = either.error)
            }
        }.getOrElse { Either.Left(error = Failure.Throwable(it)) }
}
