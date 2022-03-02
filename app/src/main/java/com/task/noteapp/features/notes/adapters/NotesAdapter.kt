package com.task.noteapp.features.notes.adapters

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.task.noteapp.R
import com.task.noteapp.core.extensions.gone
import com.task.noteapp.core.extensions.inflate
import com.task.noteapp.core.extensions.loadFromUrl
import com.task.noteapp.core.extensions.visible
import com.task.noteapp.databinding.NoteItemBinding
import com.task.noteapp.features.notes.models.NoteView
import kotlin.properties.Delegates

@SuppressLint("NotifyDataSetChanged")
class NotesAdapter : RecyclerView.Adapter<NotesAdapter.EnvironmentHolder>() {

    internal var collection: List<NoteView> by Delegates.observable(emptyList()) { _, _, _ ->
        notifyDataSetChanged()
    }
    internal var noteListener: (NoteView, View) -> Unit =
        { _: NoteView, _: View -> }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        EnvironmentHolder(parent.inflate(R.layout.note_item))

    override fun getItemCount(): Int = collection.size

    override fun onBindViewHolder(holder: EnvironmentHolder, position: Int) {
        holder.bind(collection[position], noteListener)
    }

    inner class EnvironmentHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val binding = NoteItemBinding.bind(itemView)

        fun bind(item: NoteView, characterListener: (NoteView, View) -> Unit) {
            binding.cvItem.transitionName = String.format(
                itemView.context.getString(R.string.note_card_transition_name),
                item.id
            )
            binding.ivBanner.loadFromUrl(item.image)
            binding.tvTitle.text = item.title
            binding.tvDate.text = item.creationDate
            if (item.edited) {
                binding.tvEdited.visible()
            } else {
                binding.tvEdited.gone()
            }

            binding.btnMoreInfo.setOnClickListener { characterListener(item, binding.cvItem) }
        }
    }
}
