package hu.dokabalazs.fragment

import android.Manifest
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.livinglifetechway.quickpermissions_kotlin.runWithPermissions
import com.livinglifetechway.quickpermissions_kotlin.util.QuickPermissionsOptions
import hu.dokabalazs.MainActivity
import hu.dokabalazs.R
import hu.dokabalazs.util.FragmentStore
import kotlinx.android.synthetic.main.fragment_qrscanner.*

class QRScannerFragment : Fragment() {

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		return inflater.inflate(R.layout.fragment_qrscanner, container, false)
	}

	override fun onResume() {
		super.onResume()
		Handler().post {
			runWithPermissions(
				Manifest.permission.CAMERA,
				options = QuickPermissionsOptions(
					permissionsDeniedMethod = { navigateBackFailed(view) },
					permanentDeniedMethod = { navigateBackFailed(view) }
				)
			) {
				qr_scanner.setResultHandler {
					qr_scanner.stopCamera()
					navigateBack(it.text)
				}
				qr_scanner.startCamera()
			}
		}
	}

	override fun onPause() {
		super.onPause()
		qr_scanner.stopCamera()
	}

	private fun navigateBackFailed(view: View?) {
		view?.let { Snackbar.make(it, "Camera permission is denied", 3000).show() }
		(activity as MainActivity).changeFragment(FragmentStore.foodListFragment, backStack = false, showFab = true)
	}

	private fun navigateBack(barcode: String) {
		val fragment = FragmentStore.newFoodFragment
		fragment.arguments = Bundle()
		fragment.arguments!!.putString("barcode", barcode)
		(activity as MainActivity).changeFragment(fragment, backStack = true, showFab = false)
	}
}
