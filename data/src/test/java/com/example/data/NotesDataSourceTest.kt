package com.example.data

import com.example.core.extensions.onSuccess
import com.example.core.functional.Either
import com.example.data.datasource.NotesDataSourceImp
import com.example.data.local.NotesLocal
import com.example.data.models.ActionEntity
import com.example.data.models.NoteEntity
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.`should be instance of`
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Test

class NotesDataSourceTest {

    @Test
    fun `should get notes on success`() = runBlocking {

        val local = mock<NotesLocal> {
            onBlocking {
                getNotes()
            } doReturn mockResponse
        }

        val dataSource = NotesDataSourceImp(local)

        val result = dataSource.getNotes()

        result.`should be instance of`<Either.Right<List<NoteEntity>>>()
        result.onSuccess {
            it shouldBeEqualTo mockResponse
        }

        Unit
    }

    @Test
    fun `should insert note on success`() = runBlocking {

        val local = mock<NotesLocal> {
            onBlocking { getNotes() } doReturn mockResponse
        }

        val dataSource = NotesDataSourceImp(local)

        val result = dataSource.modifyNote(mockNote, ActionEntity.INSERT)

        result.`should be instance of`<Either.Right<List<NoteEntity>>>()
        result.onSuccess {
            it shouldBeEqualTo mockResponse
        }

        Unit
    }

    @Test
    fun `should update note on success`() = runBlocking {

        val local = mock<NotesLocal> {
            onBlocking { getNotes() } doReturn mockResponse
        }

        val dataSource = NotesDataSourceImp(local)

        val result = dataSource.modifyNote(mockNote, ActionEntity.UPDATE)

        result.`should be instance of`<Either.Right<List<NoteEntity>>>()
        result.onSuccess {
            it shouldBeEqualTo mockResponse
        }

        Unit
    }

    @Test
    fun `should delete note on success`() = runBlocking {

        val local = mock<NotesLocal> {
            onBlocking { getNotes() } doReturn mockResponse
        }

        val dataSource = NotesDataSourceImp(local)

        val result = dataSource.modifyNote(mockNote, ActionEntity.DELETE)

        result.`should be instance of`<Either.Right<List<NoteEntity>>>()
        result.onSuccess {
            it shouldBeEqualTo mockResponse
        }

        Unit
    }

    companion object {
        val mockNote = NoteEntity.empty()
        val mockResponse = listOf(mockNote)
    }
}
