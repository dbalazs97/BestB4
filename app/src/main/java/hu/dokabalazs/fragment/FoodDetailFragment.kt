package hu.dokabalazs.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hu.dokabalazs.model.Food
import hu.dokabalazs.util.ResourceBinding
import hu.dokabalazs.util.dateAsReadable
import kotlinx.android.synthetic.main.food_detail.*
import java.util.*

class FoodDetailFragment : Fragment() {
	private lateinit var food: Food

	companion object {
		fun createInstance(food: Food): FoodDetailFragment {
			val fragment = FoodDetailFragment()
			val bundle = Bundle()
			bundle.putSerializable("food", food)
			fragment.arguments = bundle
			return fragment
		}
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		return inflater.inflate(hu.dokabalazs.R.layout.food_detail, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		food = arguments?.getSerializable("food") as Food?
			?: throw IllegalArgumentException("There should be a Food in FoodDetailFragment arguments")
		food_detail_name.text = food.name
		food_detail_quantity.text = food.quantity
		food_detail_image.setImageResource(ResourceBinding(food.thumbnail))
		food_detail_insertion.setText(dateAsReadable(food.insertDate))
		food_detail_expiry.setText(dateAsReadable(food.expiryDate))

		val progress = Math.round((Date() - food.insertDate) / (food.expiryDate - food.insertDate).toDouble() * 100)
		food_detail_progress.progress = progress.toInt()
	}
}

private operator fun Date.minus(date: Date): Long {
	return this.time - date.time
}
