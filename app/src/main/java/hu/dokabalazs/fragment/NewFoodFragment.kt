package hu.dokabalazs.fragment

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hu.dokabalazs.MainActivity
import hu.dokabalazs.R
import hu.dokabalazs.model.Food
import hu.dokabalazs.util.FragmentStore
import hu.dokabalazs.util.ResourceBinding
import kotlinx.android.synthetic.main.fragment_new_food.*
import java.util.*

class NewFoodFragment : Fragment() {

	private var imageResource: Int = R.drawable.food

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		return inflater.inflate(R.layout.fragment_new_food, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		arguments?.let {
			it["barcode"]?.let { barcode ->
				newfood_qr.setText(barcode.toString())
			}
		}

		newfood_expiry_date.minDate = GregorianCalendar().timeInMillis
		newfood_save_button.setOnClickListener { saveNewFood() }
		newfood_image_selector.setOnClickListener {
			NewFoodImageDialogFragment.getInstance(
				selectedImage = imageResource,
				closeListener = { newfood_image_selector.setImageResource(it); imageResource = it }
			).show(activity?.supportFragmentManager, "IMAGE_DIALOG")
		}
	}


	private fun validateFields(name: String, quantity: String, expiry: Triple<Int, Int, Int>): Boolean {
		when {
			name.isEmpty() ->
				view?.let { Snackbar.make(it, "The name can not be empty", 3000).show() }
			quantity.isEmpty() ->
				view?.let { Snackbar.make(it, "The quantity can not be empty", 3000).show() }
			GregorianCalendar(expiry.first, expiry.second, expiry.third) <= GregorianCalendar() ->
				view?.let { Snackbar.make(it, "Can not put in expired food", 3000).show() }
			else -> return true
		}
		return false
	}

	private fun saveNewFood() {
		val name = newfood_name.text.toString()
		val quantity = newfood_quantity.text.toString()
		val expiryYear = newfood_expiry_date.year
		val expiryMonth = newfood_expiry_date.month
		val expiryDay = newfood_expiry_date.dayOfMonth

		if (validateFields(name, quantity, Triple(expiryYear, expiryMonth, expiryDay))) {
			FragmentStore.foodListFragment.foodAdapter.addItems(
				Food(
					null,
					name,
					quantity,
					GregorianCalendar(expiryYear, expiryMonth, expiryDay).time,
					ResourceBinding[imageResource] ?: R.drawable.food
				)
			)

			(activity as MainActivity).changeFragment(FragmentStore.foodListFragment, backStack = false)
		}
	}
}
