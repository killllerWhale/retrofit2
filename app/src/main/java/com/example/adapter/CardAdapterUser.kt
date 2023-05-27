package com.example.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofit2.databinding.CardItemUserBinding

class CardAdapterUser(private val itemClickListener: (CardUser) -> Unit) : ListAdapter<CardUser, CardAdapterUser.CardViewHolder>(
    UserDiffUtil
) {

    inner class CardViewHolder(
        private val binding: CardItemUserBinding,
        private val itemClickListener: (CardUser) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val clickedItem = getItem(position)
                    itemClickListener(clickedItem)
                }
            }
        }

        fun bind(currentItem: CardUser) {
            binding.cardImage.setImageResource(currentItem.photo)
            binding.cardPhone.text = currentItem.phone
            binding.cardName.text = currentItem.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CardItemUserBinding.inflate(inflater, parent, false)
        return CardViewHolder(binding, itemClickListener)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

object UserDiffUtil : DiffUtil.ItemCallback<CardUser>() {
    override fun areItemsTheSame(oldItem: CardUser, newItem: CardUser): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: CardUser, newItem: CardUser): Boolean {
        return oldItem == newItem
    }
}

