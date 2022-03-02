package com.task.noteapp.features.notes.views

import android.os.Bundle
import android.view.View
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.core.exception.FailureView
import com.google.android.material.transition.MaterialElevationScale
import com.google.android.material.transition.MaterialFadeThrough
import com.task.noteapp.R
import com.task.noteapp.core.customViews.error.InformationItem.Companion.Type
import com.task.noteapp.core.extensions.close
import com.task.noteapp.core.extensions.show
import com.task.noteapp.core.functional.Status.ERROR
import com.task.noteapp.core.functional.Status.LOADED
import com.task.noteapp.core.functional.Status.LOADING
import com.task.noteapp.core.platform.BaseFragment
import com.task.noteapp.core.platform.viewBinding.viewBinding
import com.task.noteapp.databinding.NotesFragmentBinding
import com.task.noteapp.features.notes.adapters.NotesAdapter
import com.task.noteapp.features.notes.models.NoteView
import com.task.noteapp.features.notes.viewmodels.NotesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotesFragment : BaseFragment(R.layout.notes_fragment) {

    private val binding by viewBinding(NotesFragmentBinding::bind)
    private val viewModel by viewModels<NotesViewModel>()
    private val notesAdapter by lazy { NotesAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enterTransition = MaterialFadeThrough().apply {
            duration = resources.getInteger(R.integer.reply_motion_duration_large).toLong()
        }

        lifecycleScope.launchWhenStarted {
            viewModel.notesState.collect { state ->
                when (state.status) {
                    LOADING -> showSpinner(true)
                    LOADED -> {
                        showSpinner(false)
                        showLoadedState(state.success)
                    }
                    ERROR -> {
                        showSpinner(true)
                        showErrorState(state.error)
                    }
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<List<NoteView>>("key")
            ?.observe(viewLifecycleOwner) {
                showLoadedState(it)
            }
        initView()
        initListeners()
    }

    private fun initView() {
        postponeEnterTransition()
        requireView().doOnPreDraw { startPostponedEnterTransition() }

        binding.rvNotes.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = notesAdapter
        }

        binding.errorView.onClick = {
            viewModel.notes()
            binding.errorView.close()
        }

        binding.fav.setOnClickListener {
            findNavController().navigate(
                NotesFragmentDirections.actionNotesFragmentToNoteFragment(null)
            )
        }
    }

    private fun initListeners() {
        notesAdapter.noteListener = { note, view ->

            exitTransition = MaterialElevationScale(false).apply {
                duration = resources.getInteger(R.integer.reply_motion_duration_large).toLong()
            }
            reenterTransition = MaterialElevationScale(true).apply {
                duration = resources.getInteger(R.integer.reply_motion_duration_large).toLong()
            }
            val noteCardDetailTransitionName =
                getString(R.string.note_card_detail_transition_name)
            val extras = FragmentNavigatorExtras(view to noteCardDetailTransitionName)
            val directions = NotesFragmentDirections.actionNotesFragmentToNoteFragment(note)
            findNavController().navigate(directions, extras)
        }
    }

    private fun showLoadedState(notes: List<NoteView>) {
        notesAdapter.collection = notes
    }

    private fun showErrorState(error: FailureView) {
        binding.errorView.show(
            type = Type.ONE_ACTION_PRIMARY_BUTTON,
            title = getErrorTitle(error.title),
            message = getErrorMessage(error.message),
            actionOne = getString(R.string.error_screen_retry)
        )
    }

    private fun getErrorMessage(message: String?): String =
        message ?: getString(R.string.common_error_message)

    private fun getErrorTitle(title: String?): String =
        title ?: getString(R.string.common_error_title)
}
