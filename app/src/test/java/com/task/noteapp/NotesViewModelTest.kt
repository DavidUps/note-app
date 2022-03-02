package com.task.noteapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.core.exception.FailureView
import com.example.core.functional.Either
import com.example.domain.repository.NotesRepository
import com.example.domain.usescases.GetNotes
import com.example.domain.usescases.GetNotesImp
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.task.noteapp.features.notes.models.NoteView
import com.task.noteapp.features.notes.viewmodels.NotesViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class NotesViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesRule = CoroutineTestRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()
    private lateinit var viewModel: NotesViewModel

    private var repository = mock<NotesRepository>()
    private var getNote = mock<GetNotes>()

    @Before
    fun setup() {
        getNote = GetNotesImp(repository)
        viewModel = NotesViewModel(getNote)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `should emit get notes`() =
        coroutinesRule.dispatcher.runBlockingTest {

            val channel = Channel<Either<FailureView, List<NoteView>>>()
            val flow = channel.consumeAsFlow()

            doReturn(flow)
                .whenever(repository)
                .getNotes()

            val job = launch {
                channel.send(mockResponse)
                channel.close(Throwable(""))
            }

            viewModel.notes()

            viewModel.notesState.value.success shouldBeEqualTo mockNotes

            job.cancel()
        }

    companion object {
        private val mockNotes = listOf<NoteView>()
        val mockResponse = Either.Right(mockNotes)
    }
}
