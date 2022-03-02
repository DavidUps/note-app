package com.example.domain.usescases

import com.example.core.exception.Failure
import com.example.core.functional.Either
import com.example.domain.models.Note
import kotlinx.coroutines.flow.Flow

interface GetNotes {

    fun invoke(): Flow<Either<Failure, List<Note>>>
}
