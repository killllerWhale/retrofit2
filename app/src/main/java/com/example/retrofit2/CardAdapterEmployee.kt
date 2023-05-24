package com.example.retrofit2
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.CardEmployee

class CardAdapterFavourites : ListAdapter<CardEmployee, CardAdapterFavourites.CardViewHolder>(FavoriteDiffUtil) {

    class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.card_image)
        val nameView: TextView = itemView.findViewById(R.id.card_name)
        val phoneView: TextView = itemView.findViewById(R.id.card_phone)

        fun bind(currentItem: CardEmployee) {
            imageView.setImageResource(currentItem.photo)
            nameView.text = currentItem.name
            phoneView.text = currentItem.phone
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.card_item_favourites, parent, false)
        return CardViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}

object FavoriteDiffUtil : DiffUtil.ItemCallback<CardEmployee>() {
    override fun areItemsTheSame(oldItem: CardEmployee, newItem: CardEmployee) =
        oldItem.name == newItem.name

    override fun areContentsTheSame(oldItem: CardEmployee, newItem: CardEmployee) =
        oldItem == newItem
}