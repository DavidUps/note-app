package com.example.domain.usescases

import com.example.core.exception.Failure
import com.example.core.functional.Either
import com.example.domain.models.Action
import com.example.domain.models.Action.UPDATE
import com.example.domain.models.Note
import com.example.domain.repository.NotesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ModifyNotesImp @Inject constructor(
    private val repository: NotesRepository
) : ModifyNotes {

    override fun invoke(note: Note, action: Action): Flow<Either<Failure, List<Note>>> =
        flow {
            runCatching {
                if (action == UPDATE) {
                    note.let { it.edited = true }
                }
                repository.modifyNote(note = note, action = action)
            }.map { either ->
                when (either) {
                    is Either.Right -> emit(Either.Right(success = either.success))
                    is Either.Left -> emit(Either.Left(error = either.error))
                }
            }.getOrElse { emit(Either.Left(error = Failure.Throwable(it))) }
        }.flowOn(Dispatchers.IO)
}
