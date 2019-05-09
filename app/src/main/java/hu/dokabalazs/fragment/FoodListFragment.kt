package hu.dokabalazs.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hu.dokabalazs.R
import hu.dokabalazs.adapter.FoodAdapter
import hu.dokabalazs.model.Food
import kotlinx.android.synthetic.main.fragment_food_list.*
import java.util.*


class FoodListFragment : Fragment() {

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		return inflater.inflate(R.layout.fragment_food_list, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		initList()
	}

	private fun initList() {
		food_item_list.apply {
			setHasFixedSize(true)
			layoutManager = LinearLayoutManager(requireActivity())
			adapter = FoodAdapter(
				listOf(
					Food(1, "Milk", "10 litres", GregorianCalendar(2019, 5, 10).time, null),
					Food(1, "Eggs", "15", GregorianCalendar(2019, 10, 15).time, null),
					Food(1, "Salami", "750 grammes", GregorianCalendar(2020, 6, 27).time, null),
					Food(1, "Salami", "750 grammes", GregorianCalendar(2020, 6, 27).time, null),
					Food(1, "Salami", "750 grammes", GregorianCalendar(2020, 6, 27).time, null),
					Food(1, "Salami", "750 grammes", GregorianCalendar(2020, 6, 27).time, null),
					Food(1, "Salami", "750 grammes", GregorianCalendar(2020, 6, 27).time, null),
					Food(1, "Salami", "750 grammes", GregorianCalendar(2020, 6, 27).time, null),
					Food(1, "Salami", "750 grammes", GregorianCalendar(2020, 6, 27).time, null),
					Food(1, "Salami", "750 grammes", GregorianCalendar(2020, 6, 27).time, null),
					Food(1, "Salami", "750 grammes", GregorianCalendar(2020, 6, 27).time, null),
					Food(1, "Salami", "750 grammes", GregorianCalendar(2020, 6, 27).time, null),
					Food(1, "Spread Butter", "500 grammes", GregorianCalendar(2021, 2, 11).time, null)
				)
			)
		}
	}
}
