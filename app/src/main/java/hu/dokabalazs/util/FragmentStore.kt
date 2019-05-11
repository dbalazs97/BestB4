package hu.dokabalazs.util

import android.support.v4.app.Fragment
import hu.dokabalazs.fragment.*
import hu.dokabalazs.model.Food

object FragmentStore {
	val foodListFragment = FoodListFragment()
	val newFoodFragment = NewFoodFragment()
	val newFoodImageDialogFragment = NewFoodImageDialogFragment()
	val qrScannerFragment = QRScannerFragment()

	fun foodDetailFragment(food: Food) = FoodDetailFragment.createInstance(food)

	val fabState: Map<Fragment, Boolean> = mapOf(
		foodListFragment to true,
		newFoodFragment to false,
		newFoodImageDialogFragment to false,
		qrScannerFragment to false
	)
}