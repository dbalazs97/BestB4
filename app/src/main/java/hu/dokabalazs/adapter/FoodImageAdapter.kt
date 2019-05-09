package hu.dokabalazs.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RadioButton
import hu.dokabalazs.R
import kotlinx.android.synthetic.main.dialog_new_food_row.view.*

class FoodImageAdapter : RecyclerView.Adapter<FoodImageAdapter.ViewHolder>() {

	private var items: List<Int> = listOf(
		R.drawable.beer, R.drawable.bowl, R.drawable.carrot, R.drawable.corn, R.drawable.cupcake, R.drawable.fish,
		R.drawable.food, R.drawable.food_apple, R.drawable.food_croissant, R.drawable.mushroom, R.drawable.pizza,
		R.drawable.rice, R.drawable.sausage, R.drawable.silverware
	)

	private var selected: Int = 0

	inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
		val image: ImageView = itemView.dialog_new_food_image
		val radio: RadioButton = itemView.dialog_new_food_radio
		val view: View = itemView
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		val view = LayoutInflater.from(parent.context).inflate(R.layout.dialog_new_food_row, parent, false)
		return ViewHolder(view)
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		holder.image.setImageResource(items[position])
		holder.radio.isChecked = position == selected
		holder.view.setOnClickListener { itemClickHandler(holder) }
		holder.radio.setOnClickListener { itemClickHandler(holder) }
	}

	private fun itemClickHandler(holder: ViewHolder) {
		val oldPosition: Int = selected
		selected = holder.adapterPosition
		notifyItemChanged(oldPosition)
		notifyItemChanged(selected)
	}

	fun getSelectedResource(): Int {
		return items[selected]
	}

	fun setSelectedResource(selectedItem: Int) {
		selected = items.indexOf(selectedItem)
		notifyDataSetChanged()
	}

	override fun getItemCount() = items.size

}