package hu.dokabalazs.util

import hu.dokabalazs.fragment.FoodListFragment
import hu.dokabalazs.fragment.NewFoodFragment
import hu.dokabalazs.fragment.NewFoodImageDialogFragment
import hu.dokabalazs.fragment.QRScannerFragment

object FragmentStore {
	val foodListFragment = FoodListFragment()
	val newFoodFragment = NewFoodFragment()
	val newFoodImageDialogFragment = NewFoodImageDialogFragment()
	val qrScannerFragment = QRScannerFragment()
}