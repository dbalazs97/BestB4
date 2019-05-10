package hu.dokabalazs.util

import android.support.v4.app.Fragment
import hu.dokabalazs.fragment.FoodListFragment
import hu.dokabalazs.fragment.NewFoodFragment
import hu.dokabalazs.fragment.NewFoodImageDialogFragment
import hu.dokabalazs.fragment.QRScannerFragment

object FragmentStore {
	val foodListFragment = FoodListFragment()
	val newFoodFragment = NewFoodFragment()
	val newFoodImageDialogFragment = NewFoodImageDialogFragment()
	val qrScannerFragment = QRScannerFragment()

	val fabState: Map<Fragment, Boolean> = mapOf(
		foodListFragment to true,
		newFoodFragment to false,
		newFoodImageDialogFragment to false,
		qrScannerFragment to false
	)
}