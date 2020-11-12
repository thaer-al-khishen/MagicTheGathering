package com.example.magicthegathering.Adapters

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.magicthegathering.Models.Card
import com.example.magicthegathering.R
import com.example.magicthegathering.databinding.CardRowBinding
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row_card.view.*

class VerticalCardsAdapter(private val cards: ArrayList<Card>, val iCardsItemSelected: ICardsItemSelected) :
    RecyclerView.Adapter<VerticalCardsAdapter.CardsItemViewHolder>() {
    private val viewPool = RecyclerView.RecycledViewPool()
    override fun getItemCount(): Int {
        return cards.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardsItemViewHolder {
        return CardsItemViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.row_card,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CardsItemViewHolder, position: Int) {
        val model = cards[position]
        val innerClass = cards[position].rulings
        holder.binding.nameText.text = "Name: " + model.name
        holder.binding.artistText.text = "Artist: " + model.artist
        holder.binding.rarityText.text = "Rarity: " + model.rarity
        holder.binding.powerText.text = "Power: " + model.power
        holder.binding.toughnessText.text = "Toughness: " + model.toughness
        holder.binding.flavorText.text = "Flavor: " + model.flavor

        if (!TextUtils.isEmpty(model.imageUrl))
            Picasso.get().load(model.imageUrl).placeholder(R.drawable.news_ic_placeholder)
                .into(holder.binding.categoryDetailsAllImage)
        holder.binding.parentLayout.setOnClickListener {
            iCardsItemSelected.onCardsItemsClicked(position)
        }

        val childLayoutManager = GridLayoutManager(holder.itemView.rvSub.context, 2)

        holder.itemView.rvSub.apply {
            layoutManager = childLayoutManager
            adapter = SubAdapter(cards.get(position))
            setRecycledViewPool(viewPool)
        }
    }


    class CardsItemViewHolder(val binding: CardRowBinding) :
        RecyclerView.ViewHolder(binding.root)

    interface ICardsItemSelected {
        fun onCardsItemsClicked(position: Int)
    }
}
