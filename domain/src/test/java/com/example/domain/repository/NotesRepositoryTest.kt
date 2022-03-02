package com.example.domain.repository

import com.example.core.extensions.mapSuccess
import com.example.core.extensions.onSuccess
import com.example.core.functional.Either
import com.example.data.datasource.NotesDataSourceImp
import com.example.data.models.ActionEntity
import com.example.domain.models.Action
import com.example.domain.models.Note
import com.example.domain.models.toData
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.`should be instance of`
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Test

class NotesRepositoryTest {

    @Test
    fun `should get notes on success`() = runBlocking {

        val dataSource = mock<NotesDataSourceImp> {
            onBlocking {
                getNotes()
            } doReturn mockResponse.mapSuccess { it.map { it.toData() } }
        }

        val repository = NotesRepositoryImp(dataSource)

        val result = repository.getNotes()

        result.`should be instance of`<Either.Right<List<Note>>>()
        result.onSuccess { notes ->
            notes.first().id shouldBeEqualTo mockNotes.first().id
            notes.first().title shouldBeEqualTo mockNotes.first().title
            notes.first().message shouldBeEqualTo mockNotes.first().message
            notes.first().image shouldBeEqualTo mockNotes.first().image
            notes.first().creationDate shouldBeEqualTo mockNotes.first().creationDate
            notes.first().edited shouldBeEqualTo mockNotes.first().edited
        }

        Unit
    }

    @Test
    fun `should insert note on success`() = runBlocking {

        val dataSource = mock<NotesDataSourceImp> {
            onBlocking {
                modifyNote(mockNote.toData(), ActionEntity.INSERT)
            } doReturn mockResponse.mapSuccess { it.map { it.toData() } }
        }

        val repository = NotesRepositoryImp(dataSource)

        val result = repository.modifyNote(mockNote, Action.INSERT)

        result.`should be instance of`<Either.Right<List<Note>>>()
        result.onSuccess { notes ->
            notes.first().id shouldBeEqualTo mockNotes.first().id
            notes.first().title shouldBeEqualTo mockNotes.first().title
            notes.first().message shouldBeEqualTo mockNotes.first().message
            notes.first().image shouldBeEqualTo mockNotes.first().image
            notes.first().creationDate shouldBeEqualTo mockNotes.first().creationDate
            notes.first().edited shouldBeEqualTo mockNotes.first().edited
        }

        Unit
    }

    @Test
    fun `should update note on success`() = runBlocking {

        val dataSource = mock<NotesDataSourceImp> {
            onBlocking {
                modifyNote(mockNote.toData(), ActionEntity.UPDATE)
            } doReturn mockResponse.mapSuccess { it.map { it.toData() } }
        }

        val repository = NotesRepositoryImp(dataSource)

        val result = repository.modifyNote(mockNote, Action.UPDATE)

        result.`should be instance of`<Either.Right<List<Note>>>()
        result.onSuccess { notes ->
            notes.first().id shouldBeEqualTo mockNotes.first().id
            notes.first().title shouldBeEqualTo mockNotes.first().title
            notes.first().message shouldBeEqualTo mockNotes.first().message
            notes.first().image shouldBeEqualTo mockNotes.first().image
            notes.first().creationDate shouldBeEqualTo mockNotes.first().creationDate
            notes.first().edited shouldBeEqualTo mockNotes.first().edited
        }

        Unit
    }

    @Test
    fun `should delete note on success`() = runBlocking {

        val dataSource = mock<NotesDataSourceImp> {
            onBlocking {
                modifyNote(mockNote.toData(), ActionEntity.DELETE)
            } doReturn mockResponse.mapSuccess { it.map { it.toData() } }
        }

        val repository = NotesRepositoryImp(dataSource)

        val result = repository.modifyNote(mockNote, Action.DELETE)

        result.`should be instance of`<Either.Right<List<Note>>>()
        result.onSuccess { notes ->
            notes.first().id shouldBeEqualTo mockNotes.first().id
            notes.first().title shouldBeEqualTo mockNotes.first().title
            notes.first().message shouldBeEqualTo mockNotes.first().message
            notes.first().image shouldBeEqualTo mockNotes.first().image
            notes.first().creationDate shouldBeEqualTo mockNotes.first().creationDate
            notes.first().edited shouldBeEqualTo mockNotes.first().edited
        }

        Unit
    }

    companion object {
        val mockNote = Note.empty()
        val mockNotes = listOf(mockNote)
        val mockResponse = Either.Right(mockNotes)
    }
}
