package hu.dokabalazs.fragment

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.LayoutInflater
import android.view.View
import hu.dokabalazs.R
import hu.dokabalazs.adapter.FoodImageAdapter


class NewFoodImageDialogFragment : DialogFragment() {

	companion object {
		fun getInstance(selectedImage: Int = 0, closeListener: ((Int) -> Unit)? = null): NewFoodImageDialogFragment {
			val dialog = NewFoodImageDialogFragment()
			dialog.arguments = Bundle()
			dialog.arguments!!.putInt("selectedImage", selectedImage)
			dialog.closeListener = closeListener
			return dialog
		}
	}

	private val foodImageAdapter = FoodImageAdapter()
	var closeListener: ((Int) -> Unit)? = null

	override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
		return AlertDialog.Builder(requireContext())
			.setTitle(R.string.new_food_image_title)
			.setView(getContentView())
			.setPositiveButton(R.string.ok) { _, _ ->
				closeListener?.let { it(foodImageAdapter.getSelectedResource()) }
			}
			.setNegativeButton(R.string.cancel) { _, _ ->

			}
			.create()
	}

	@SuppressLint("InflateParams")
	private fun getContentView(): View {
		val view = LayoutInflater.from(context).inflate(R.layout.dialog_new_food_image, null)
		initList(view)
		return view
	}

	private fun initList(view: View) {
		foodImageAdapter.setSelectedResource(arguments?.getInt("selectedImage") ?: R.drawable.food)
		view.findViewById<RecyclerView>(R.id.dialog_new_food_list)?.apply {
			setHasFixedSize(true)
			layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
			adapter = foodImageAdapter
		}
	}
}