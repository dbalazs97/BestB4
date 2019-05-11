package hu.dokabalazs.adapter

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import hu.dokabalazs.R
import hu.dokabalazs.db.FoodDatabase
import hu.dokabalazs.model.Food
import hu.dokabalazs.util.ResourceBinding
import hu.dokabalazs.util.dateAsReadable
import hu.dokabalazs.util.diffAsString
import kotlinx.android.synthetic.main.food_item_row.view.*
import java.util.*
import kotlin.concurrent.thread

class FoodAdapter(private var items: List<Food>) : RecyclerView.Adapter<FoodAdapter.ViewHolder>() {
	var onItemClickListener: ((Food) -> Unit)? = null

	inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
		val name: TextView = itemView.food_item_name
		val quantity: TextView = itemView.food_item_quantity
		val bestBefore: TextView = itemView.food_item_best_before
		val expiry: TextView = itemView.food_item_expiry
		val thumbnail: ImageView = itemView.food_item_image
	}


	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		val view = LayoutInflater.from(parent.context).inflate(R.layout.food_item_row, parent, false)
		return ViewHolder(view)
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		val (_, name, quantity, _, expiryDate, thumbnail) = items[position]
		with(holder) {
			this.name.text = name
			this.quantity.text = quantity
			this.bestBefore.text = dateAsReadable(expiryDate)
			this.expiry.text = diffAsString(Date(), expiryDate)
			this.thumbnail.setImageResource(ResourceBinding(thumbnail))

			itemView.setOnClickListener { onItemClickListener?.invoke(items[holder.adapterPosition]) }
			if (expiryDate < Date()) {
				itemView.setBackgroundColor(Color.LTGRAY)
				this.name.setTextColor(Color.RED)
			}
		}
	}

	override fun getItemCount() = items.size

	fun addItems(vararg food: Food) {
		items = items + food
		thread { (+FoodDatabase).insertAll(food) }
		notifyItemRangeInserted(items.size - food.size, food.size)
	}

	fun deleteAt(position: Int) {
		val food = items[position]
		items = items.filterIndexed { i, _ -> i != position }
		thread { (+FoodDatabase).deleteAll(food) }
		notifyItemRemoved(position)
	}

	fun replaceWith(old: Food, new: Food) {
		items = items.map { if (it == old) new else it }
		thread {
			(+FoodDatabase).deleteAll(old)
			(+FoodDatabase).insert(new)
		}
	}

	fun getRotten(): List<Food> {
		return items.filter { it.expiryDate <= Date() }
	}

	fun getAlmostRotten(daysBefore: Int): List<Food> {
		return items.filter {
			Calendar.getInstance().apply {
				time = it.expiryDate
				add(Calendar.DATE, -daysBefore)
			}.time <= Date()
		}
	}
}