
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.magicthegathering.R
import com.example.magicthegathering.databinding.CardHorizontalRowBinding

class HorizontalCardsAdapter (private val cards: ArrayList<Card>, val iCardsItemSelected: ICardsItemSelected) :
    RecyclerView.Adapter<HorizontalCardsAdapter.CardsItemViewHolder>() {

    override fun getItemCount(): Int {
        return cards.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardsItemViewHolder {
        return CardsItemViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.row_horizontal_card,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CardsItemViewHolder, position: Int) {
        val model = cards[position]
        holder.binding.nameText.text = model.name
        if (!TextUtils.isEmpty(model.imageUrl))
            Glide.with(holder.itemView.context)
                .asBitmap()
                .load(model.imageUrl)
                .into(holder.binding.categoryDetailsAllImage)
        holder.binding.parentLayout.setOnClickListener {
            iCardsItemSelected.onCardsItemsClicked(position)
        }
    }


    class CardsItemViewHolder(val binding: CardHorizontalRowBinding) :
        RecyclerView.ViewHolder(binding.root)

    interface ICardsItemSelected {
        fun onCardsItemsClicked(position: Int)
    }
}
