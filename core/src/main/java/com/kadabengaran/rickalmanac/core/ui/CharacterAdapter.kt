package com.kadabengaran.rickalmanac.core.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kadabengaran.rickalmanac.core.R
import com.kadabengaran.rickalmanac.core.databinding.ItemListCharacterBinding
import com.kadabengaran.rickalmanac.core.domain.model.Character

class CharacterAdapter: RecyclerView.Adapter<CharacterAdapter.ListViewHolder>() {

    private var listData = ArrayList<Character>()
    var onItemClick: ((Int) -> Unit)? = null

    fun setData(newListData: List<Character>?) {
        if (newListData == null) return
        listData.clear()
        listData.addAll(newListData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_list_character, parent, false))

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = listData[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int {
        return listData.size
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
            binding.root.setOnClickListener { onItemClick?.invoke(listData[adapterPosition].characterId) }
        }
    }

}