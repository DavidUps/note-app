package com.task.noteapp.core.platform

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.core.exception.FailureView
import com.task.noteapp.MainActivity

abstract class BaseFragment(layout: Int) : Fragment() {

    val layouID = layout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        inflater.inflate(layouID, container, false)

    internal fun showProgress() = progressStatus(View.VISIBLE)

    internal fun hideProgress() = progressStatus(View.GONE)

    private fun progressStatus(viewStatus: Int) =
        with(activity) {
            if (this is MainActivity) {
//                this.progress.visibility = viewStatus
            }
        }

    internal fun showSpinner(show: Boolean) {
        when (show) {
            true -> showProgress()
            false -> hideProgress()
        }
    }

    internal fun navigateUp() {
        findNavController().navigateUp()
    }

    internal open fun handleShowSpinner(show: Boolean?) {
        showSpinner(show ?: false)
    }

    internal open fun handleFailure(failure: FailureView?) {
        when (failure) {
            else -> toast("${failure?.title}")
        }
    }

    fun toast(text: String) {
        Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show()
    }
}
