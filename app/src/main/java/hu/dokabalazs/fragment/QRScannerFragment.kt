package hu.dokabalazs.fragment

import android.Manifest
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.livinglifetechway.quickpermissions_kotlin.runWithPermissions
import com.livinglifetechway.quickpermissions_kotlin.util.QuickPermissionsOptions
import hu.dokabalazs.MainActivity
import hu.dokabalazs.R
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
					Toast.makeText(activity, it.text, Toast.LENGTH_LONG).show()
					navigateBack()
				}
				qr_scanner.startCamera()
			}
		}
	}

	private fun navigateBackFailed(view: View?) {
		view?.let { Snackbar.make(it, "Camera permission is denied", 3000).show() }
		navigateBack()
	}

	private fun navigateBack() {
		(activity as MainActivity).changeFragment(FoodListFragment())
	}
}
