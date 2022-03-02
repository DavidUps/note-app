package com.example.domain.usescases

import com.example.core.extensions.onSuccess
import com.example.core.functional.Either
import com.example.data.datasource.NotesDataSourceImp
import com.example.data.models.NoteEntity
import com.example.domain.UnitTest
import com.example.domain.models.Note
import com.example.domain.repository.NotesRepository
import com.example.domain.repository.NotesRepositoryImp
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.`should be instance of`
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Test

class GetNotesImpTest : UnitTest() {

    private lateinit var repository: NotesRepository
    private lateinit var useCase: GetNotes

    @Test
    fun `should get notes on success`() = runBlocking {

        val mockNotes = listOf<NoteEntity>()
        val notesDomain = Either.Right(success = mockNotes)

        val notesDataSource = mock<NotesDataSourceImp> {
            onBlocking { getNotes() } doReturn notesDomain
        }

        repository = NotesRepositoryImp(notesDataSource)

        useCase = GetNotesImp(repository)

        val flow = useCase.invoke()

        flow.collect {
            it.`should be instance of`<Either.Right<List<Note>>>()
            it.onSuccess { notes ->
                notes shouldBeEqualTo mockNotes
            }
        }
    }
}
