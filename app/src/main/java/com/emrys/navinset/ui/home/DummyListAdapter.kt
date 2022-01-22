package com.emrys.navinset.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.emrys.navinset.databinding.ItemDummyBinding

class DummyListAdapter(
    private val onClick: () -> Unit
): ListAdapter<DummyData, DummyListAdapter.DummyViewHolder>(DummyData.diffCallback) {

    inner class DummyViewHolder(
        private val binding: ItemDummyBinding
    ): RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                onClick()
            }
        }

        fun bind(data: DummyData) {
            binding.textView.text = data.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DummyViewHolder {
        return DummyViewHolder(
            ItemDummyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: DummyViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

data class DummyData(
    val id: String,
    val name: String,
) {
    companion object {
        val diffCallback = object: DiffUtil.ItemCallback<DummyData>() {
            override fun areItemsTheSame(oldItem: DummyData, newItem: DummyData): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: DummyData, newItem: DummyData): Boolean {
                return oldItem == newItem
            }

            override fun getChangePayload(oldItem: DummyData, newItem: DummyData): Any? {
                return when {
                    oldItem.name != newItem.name -> 1
                    else -> null
                }
            }
        }
    }
}