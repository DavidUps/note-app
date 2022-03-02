package com.example.data.datasource

import com.example.core.exception.Failure
import com.example.core.extensions.empty
import com.example.core.functional.Either
import com.example.data.local.NotesLocal
import com.example.data.models.ActionEntity
import com.example.data.models.NoteEntity
import javax.inject.Inject

class NotesDataSourceImp @Inject constructor(
    private val local: NotesLocal
) : NotesDataSource {

    override suspend fun getNotes(): Either<Failure, List<NoteEntity>> {
        return runCatching {
            local.getNotes()
        }.map {
            if (it != null) {
                Either.Right(success = it)
            } else {
                Either.Left(error = Failure.CustomError(0, "Can't access to the database"))
            }
        }
            .getOrElse {
                Either.Left(error = Failure.Throwable(it))
            }
    }

    override suspend fun modifyNote(
        note: NoteEntity,
        action: ActionEntity
    ): Either<Failure, List<NoteEntity>> {
        return runCatching {
            when (action) {
                ActionEntity.INSERT -> local.insertNote(note)
                ActionEntity.UPDATE -> local.updateNote(note)
                ActionEntity.DELETE -> local.deleteNote(note)
                else -> null
            }
        }.map {
            if (it != null) {
                getNotes()
            } else {
                Either.Left(error = Failure.CustomError(Int.empty(), "Bad action"))
            }
        }.getOrElse {
            Either.Left(error = Failure.Throwable(it))
        }
    }
}
