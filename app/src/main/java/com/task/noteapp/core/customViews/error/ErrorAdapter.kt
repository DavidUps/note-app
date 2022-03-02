package com.task.noteapp.core.customViews.error

import android.annotation.SuppressLint
import android.text.method.LinkMovementMethod
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.task.noteapp.R
import com.task.noteapp.core.extensions.inflate
import com.task.noteapp.core.extensions.loadFromUrl
import com.task.noteapp.databinding.ErrorInformationViewBinding
import kotlin.properties.Delegates

@SuppressLint("NotifyDataSetChanged")
class ErrorAdapter : RecyclerView.Adapter<ErrorAdapter.ViewHolder>() {

    internal var collection: List<InformationItem> by Delegates.observable(emptyList()) { _, _, _ ->
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(parent.inflate(R.layout.error_information_view))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(collection[position])
    }

    override fun getItemCount() = collection.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val binding = ErrorInformationViewBinding.bind(itemView)

        fun bind(informationItem: InformationItem) {
            if (informationItem.icon != null) {
                binding.ivBanner.setImageDrawable(informationItem.icon)
            } else {
                binding.ivBanner.loadFromUrl("https://icons.iconarchive.com/icons/paomedia/small-n-flat/1024/sign-error-icon.png")
            }

            binding.tvTitleInformation.text = informationItem.title

            if (informationItem.message != null) {
                binding.tvMessageInformation.text = informationItem.message
            } else {
                binding.tvMessageInformation.movementMethod = LinkMovementMethod.getInstance()
                binding.tvMessageInformation.text = informationItem.messageSpanned
            }
        }
    }
}
