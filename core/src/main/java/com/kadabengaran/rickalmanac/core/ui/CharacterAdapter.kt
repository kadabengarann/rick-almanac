package com.kadabengaran.rickalmanac.core.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kadabengaran.rickalmanac.core.R
import com.kadabengaran.rickalmanac.core.databinding.ItemListCharacterBinding
import com.kadabengaran.rickalmanac.core.domain.model.Character

class CharacterAdapter : RecyclerView.Adapter<CharacterAdapter.ListViewHolder>() {

    var listData: List<Character>?
        get() = recyclerListDiffer.currentList
        set(value) = recyclerListDiffer.submitList(value)

    var onItemClick: ((Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_list_character, parent, false))

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = listData?.get(position)
        if (data != null) {
            holder.bind(data)
        }
    }

    override fun getItemCount(): Int {
        return listData?.size ?: 0
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemListCharacterBinding.bind(itemView)
        fun bind(data: Character) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(data.image)
                    .into(ivItemImage)
                tvItemTitle.text = data.name
                tvItemSubtitle.text = data.species
            }
        }

        init {
            binding.root.setOnClickListener { listData?.get(adapterPosition)?.characterId?.let { onItemClick?.invoke(it) } }
        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<Character>() {
        override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem.characterId == newItem.characterId
        }

        override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem == newItem
        }
    }
    private val recyclerListDiffer = AsyncListDiffer(this, differCallback)
}
