package com.task.noteapp.features.notes.views

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.domain.models.Action
import com.google.android.material.transition.MaterialContainerTransform
import com.task.noteapp.R
import com.task.noteapp.core.extensions.gone
import com.task.noteapp.core.extensions.loadFromUrl
import com.task.noteapp.core.extensions.visible
import com.task.noteapp.core.functional.Status
import com.task.noteapp.core.platform.BaseFragment
import com.task.noteapp.core.platform.viewBinding.viewBinding
import com.task.noteapp.databinding.NoteFragmentBinding
import com.task.noteapp.features.notes.models.NoteView
import com.task.noteapp.features.notes.viewmodels.NoteViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NoteFragment : BaseFragment(R.layout.note_fragment) {

    private val arguments by navArgs<NoteFragmentArgs>()
    private val binding by viewBinding(NoteFragmentBinding::bind)
    private val viewModel by viewModels<NoteViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedElementEnterTransition = MaterialContainerTransform().apply {
            duration = resources.getInteger(R.integer.reply_motion_duration_large).toLong()
        }

        lifecycleScope.launchWhenStarted {
            viewModel.notesState.collect { state ->
                if (state.status == Status.LOADED && state.success != null) {
                    val navController = findNavController()
                    navController.previousBackStackEntry?.savedStateHandle?.set(
                        "key",
                        state.success
                    )
                    navController.popBackStack()
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initListeners()
    }

    private fun initView() {
        if (arguments.note != null) {
            showViewMode(arguments.note!!)
        } else {
            showEditMode()
        }
    }

    private fun initListeners() {
        binding.btnEdit.setOnClickListener {
            enabledMode()
        }

        binding.btnSave.setOnClickListener {
            if (arguments.note != null) {
                viewModel.modifyNotes(
                    NoteView(
                        id = arguments.note!!.id,
                        title = binding.etTitle.text.toString(),
                        message = binding.etMessage.text.toString(),
                        image = binding.etImageUrl.text.toString()
                    ),
                    Action.UPDATE
                )
            } else {
                viewModel.modifyNotes(
                    NoteView(
                        title = binding.etTitle.text.toString(),
                        message = binding.etMessage.text.toString(),
                        image = binding.etImageUrl.text.toString()
                    ),
                    Action.INSERT
                )
            }
        }

        binding.btnDelete.setOnClickListener {
            if (arguments.note != null) {
                viewModel.modifyNotes(arguments.note!!, Action.DELETE)
            }
        }
    }

    private fun showViewMode(note: NoteView) {

        binding.ivBanner.loadFromUrl(note.image)
        with(binding.etImageUrl) {
            isEnabled = false
            setText(note.image)
        }
        with(binding.etTitle) {
            isEnabled = false
            setText(note.title)
        }
        with(binding.etMessage) {
            isEnabled = false
            setText(note.message)
        }
        binding.btnSave.gone()
        binding.btnEdit.visible()
    }

    private fun showEditMode() {
        enabledMode()
    }

    private fun enabledMode() {
        binding.etImageUrl.isEnabled = true
        binding.etTitle.isEnabled = true
        binding.etMessage.isEnabled = true
        binding.btnSave.visible()
        binding.btnEdit.gone()
    }
}
