package com.task.noteapp.core.customViews.error

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.text.Spanned
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.task.noteapp.R
import com.task.noteapp.core.customViews.error.InformationItem.Companion.Type
import com.task.noteapp.core.extensions.gone
import com.task.noteapp.core.extensions.visible
import com.task.noteapp.databinding.ErrorViewContainerBinding

class ErrorView(context: Context, attrs: AttributeSet?) :
    ConstraintLayout(context, attrs) {

    private var binding: ErrorViewContainerBinding =
        ErrorViewContainerBinding.inflate(LayoutInflater.from(context), this, true)

    var onClick: (Int) -> Unit = { }

    private val errorAdapter = ErrorAdapter()

    init {
        var greyBackground: Boolean
        attrs.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.ErrorView)
            greyBackground =
                typedArray.getBoolean(R.styleable.ErrorView_errorViewGreyBackgroundColor, true)
            when (typedArray.getInt(R.styleable.ErrorView_errorViewActions, 1)) {
                2 -> {
                    (binding.btns.layoutParams as ViewGroup.MarginLayoutParams).setMargins(
                        0,
                        0,
                        0,
                        8
                    )
                    (binding.btnActionOne.layoutParams as ViewGroup.MarginLayoutParams).setMargins(
                        0,
                        0,
                        0,
                        -16
                    )
                }
            }
            typedArray.recycle()
        }

        if (greyBackground) {
            binding.mainContainer.background =
                ColorDrawable(ContextCompat.getColor(context, R.color.grey))
            binding.view.setImageResource(R.drawable.grey_scroll_mask)
        } else {
            binding.mainContainer.background =
                ColorDrawable(ContextCompat.getColor(context, R.color.white))
            binding.view.setImageResource(R.drawable.white_scroll_mask)
        }
    }

    fun init(type: Type, title: String, message: String, actionOne: String?) {
        setView(
            type,
            listOf(InformationItem(null, title, message, null, actionOne, null, type)),
            actionOne,
            null
        )
    }

    fun init(icon: Drawable?, type: Type, title: String, message: String, actionOne: String?) {
        setView(
            type,
            listOf(InformationItem(icon, title, message, null, actionOne, null, type)),
            actionOne,
            null
        )
    }

    fun initSpanned(type: Type, title: String, message: Spanned, actionOne: String?) {
        setView(
            type,
            listOf(InformationItem(null, title, null, message, actionOne, null, type)),
            actionOne,
            null
        )
    }

    fun initSpanned(
        icon: Drawable?,
        type: Type,
        title: String,
        message: Spanned,
        actionOne: String?
    ) {
        setView(
            type,
            listOf(InformationItem(icon, title, null, message, actionOne, null, type)),
            actionOne,
            null
        )
    }

    fun init(type: Type, title: String, message: String, actionOne: String?, actionTwo: String?) {
        setView(
            type,
            listOf(InformationItem(null, title, message, null, actionOne, actionTwo, type)),
            actionOne,
            actionTwo
        )
    }

    fun initSpanned(
        type: Type,
        title: String,
        message: Spanned,
        actionOne: String?,
        actionTwo: String?
    ) {
        setView(
            type,
            listOf(InformationItem(null, title, null, message, actionOne, actionTwo, type)),
            actionOne,
            actionTwo
        )
    }

    private fun setView(
        type: Type,
        item: List<InformationItem>,
        actionOne: String?,
        actionTwo: String?
    ) {
        binding.rvError.apply {
            this.layoutManager = LinearLayoutManager(context)
            this.adapter = errorAdapter
        }

        errorAdapter.collection = item

        when (type) {
            Type.INFORMATION -> {
                binding.view.gone()
                binding.btns.gone()
            }
            Type.ONE_ACTION_PRIMARY_BUTTON -> {
                with(binding.btnActionOne) {
                    visible()
                    text = actionOne
                }
                with(binding.btnActionTwo) {
                    gone()
                }
            }
            Type.ONE_ACTION_SECONDARY_BUTTON -> {
                with(binding.btnActionOne) {
                    visible()
                    text = actionOne
                }
                with(binding.btnActionTwo) {
                    gone()
                }
            }
            Type.TWO_ACTIONS -> {
                with(binding.btnActionOne) {
                    visible()
                    text = actionOne
                }
                with(binding.btnActionTwo) {
                    visible()
                    text = actionTwo
                }
            }
        }

        binding.btnActionOne.setOnClickListener { onClick(ACTION_ONE) }
        binding.btnActionTwo.setOnClickListener { onClick(ACTION_TWO) }
    }

    companion object {
        const val ACTION_ONE = 1
        const val ACTION_TWO = 2
    }
}
