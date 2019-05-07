package hu.dokabalazs.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import hu.dokabalazs.R
import hu.dokabalazs.model.Food
import kotlinx.android.synthetic.main.food_item_row.view.*
import java.text.SimpleDateFormat
import java.util.*

class FoodAdapter(private val items: Array<Food>) : RecyclerView.Adapter<FoodAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.food_item_name
        val quantity: TextView = itemView.food_item_quantity
        val bestBefore: TextView = itemView.food_item_best_before
        val expiry: TextView = itemView.food_item_expiry
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.food_item_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val (_, name, quantity, expiryDate) = items[position]
        holder.name.text = name
        holder.quantity.text = quantity
        holder.bestBefore.text = SimpleDateFormat("yyyy. MM. dd.", Locale.GERMAN).format(expiryDate)
        holder.expiry.text = SimpleDateFormat("yyyy. MM. dd.", Locale.GERMAN).format(expiryDate)
    }

    override fun getItemCount() = items.size
}