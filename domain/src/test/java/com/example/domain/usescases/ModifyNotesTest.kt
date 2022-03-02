package com.example.domain.usescases

import com.example.core.extensions.onSuccess
import com.example.core.functional.Either
import com.example.data.datasource.NotesDataSourceImp
import com.example.data.models.ActionEntity
import com.example.data.models.NoteEntity
import com.example.domain.UnitTest
import com.example.domain.models.Action
import com.example.domain.models.Note
import com.example.domain.models.toData
import com.example.domain.repository.NotesRepository
import com.example.domain.repository.NotesRepositoryImp
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.`should be instance of`
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner.Silent::class)
class ModifyNotesTest : UnitTest() {

    private lateinit var repository: NotesRepository
    private lateinit var useCase: ModifyNotes

    @Test
    fun `should insert notes on success`() = runBlocking {

        val notesDataSource = mock<NotesDataSourceImp> {
            onBlocking { modifyNote(mockNote.toData(), ActionEntity.INSERT) } doReturn notesDomain
        }

        repository = NotesRepositoryImp(notesDataSource)

        useCase = ModifyNotesImp(repository)

        val flow = useCase.invoke(mockNote, Action.INSERT)

        flow.collect {
            it.`should be instance of`<Either.Right<List<Note>>>()
            it.onSuccess { notes ->
                notes shouldBeEqualTo mockNotes
            }
        }
    }

    @Test
    fun `should update notes on success`() = runBlocking {

        val notesDataSource = mock<NotesDataSourceImp> {
            onBlocking { modifyNote(mockNote.toData(), ActionEntity.INSERT) } doReturn notesDomain
        }

        repository = NotesRepositoryImp(notesDataSource)

        useCase = ModifyNotesImp(repository)

        val flow = useCase.invoke(mockNote, Action.INSERT)

        flow.collect {
            it.`should be instance of`<Either.Right<List<Note>>>()
            it.onSuccess { notes ->
                notes shouldBeEqualTo mockNotes
            }
        }
    }

    @Test
    fun `should delete notes on success`() = runBlocking {

        val notesDataSource = mock<NotesDataSourceImp> {
            onBlocking { modifyNote(mockNote.toData(), ActionEntity.DELETE) } doReturn notesDomain
        }

        repository = NotesRepositoryImp(notesDataSource)

        useCase = ModifyNotesImp(repository)

        val flow = useCase.invoke(mockNote, Action.DELETE)

        flow.collect {
            it.`should be instance of`<Either.Right<List<Note>>>()
            it.onSuccess { notes ->
                notes shouldBeEqualTo mockNotes
            }
        }
    }

    companion object {
        val mockNote = Note.empty()
        val mockNotes = listOf<NoteEntity>()
        val notesDomain = Either.Right(success = mockNotes)
    }
}
