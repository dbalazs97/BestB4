package hu.dokabalazs.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hu.dokabalazs.adapter.FoodAdapter
import hu.dokabalazs.db.FoodDatabase
import kotlinx.android.synthetic.main.fragment_food_list.*
import kotlin.concurrent.thread


class FoodListFragment : Fragment() {
	lateinit var foodAdapter: FoodAdapter

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		return inflater.inflate(hu.dokabalazs.R.layout.fragment_food_list, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		initList()
	}

	private fun initList() {

		val itemTouchHelperCallback =
			object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
				override fun onMove(
					recyclerView: RecyclerView,
					viewHolder: RecyclerView.ViewHolder,
					target: RecyclerView.ViewHolder
				): Boolean = false

				override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
					foodAdapter.deleteAt(viewHolder.adapterPosition)
				}
			}

		thread {
			val foods = FoodDatabase.get().foodDao().getAllFoods()
			foodAdapter = FoodAdapter(foods)
			requireActivity().runOnUiThread {
				food_item_list.apply {
					setHasFixedSize(true)
					layoutManager = LinearLayoutManager(requireActivity())
					adapter = foodAdapter
				}
				ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(food_item_list)
			}
		}
	}
}
