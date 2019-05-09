package hu.dokabalazs.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hu.dokabalazs.R
import kotlinx.android.synthetic.main.fragment_new_food.*

class NewFoodFragment : Fragment() {

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
	}
}