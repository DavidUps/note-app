package com.task.noteapp.features.notes.models

import com.example.core.exception.FailureView
import com.task.noteapp.core.functional.Status

data class NotesState(
    var status: Status = Status.LOADING,
    var error: FailureView = FailureView(),
    var success: List<NoteView> = listOf()
)
