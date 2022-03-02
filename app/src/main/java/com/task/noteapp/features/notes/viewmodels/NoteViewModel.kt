package com.task.noteapp.features.notes.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.exception.FailureView
import com.example.core.exception.toView
import com.example.core.extensions.cancelIfActive
import com.example.core.extensions.onFailure
import com.example.core.extensions.onSuccess
import com.example.domain.models.Action
import com.example.domain.usescases.ModifyNotes
import com.task.noteapp.core.functional.Status
import com.task.noteapp.features.notes.models.NoteView
import com.task.noteapp.features.notes.models.NotesState
import com.task.noteapp.features.notes.models.toDomain
import com.task.noteapp.features.notes.models.toView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val modifyNotes: ModifyNotes
) : ViewModel() {

    private var getNotesJob: Job? = null

    private var _notesState = MutableStateFlow(NotesState())
    val notesState get() = _notesState.asStateFlow()

    fun modifyNotes(note: NoteView, action: Action) {
        getNotesJob.cancelIfActive()
        getNotesJob = viewModelScope.launch {
            modifyNotes.invoke(note = note.toDomain(), action = action)
                .onStart { _notesState.update { it.copy(status = Status.LOADING) } }
                .onCompletion { _notesState.update { it.copy(status = Status.LOADED) } }
                .catch { throwable ->
                    _notesState.update {
                        NotesState(
                            status = Status.ERROR,
                            error = FailureView(message = throwable.message)
                        )
                    }
                }
                .collect { either ->
                    either.onFailure { failure ->
                        _notesState.update {
                            it.copy(
                                status = Status.ERROR,
                                error = failure.toView()
                            )
                        }
                    }
                    either.onSuccess { notes ->
                        _notesState.update {
                            it.copy(
                                status = Status.LOADED,
                                success = notes.map { it.toView() }
                            )
                        }
                    }
                }
        }
    }
}
