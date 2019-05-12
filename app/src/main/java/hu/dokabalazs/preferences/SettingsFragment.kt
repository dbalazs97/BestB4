package hu.dokabalazs.preferences

import android.os.Bundle
import android.support.v7.preference.PreferenceFragmentCompat
import hu.dokabalazs.R


class SettingsFragment : PreferenceFragmentCompat() {
	override fun onCreatePreferences(bundle: Bundle?, s: String?) {
		setPreferencesFromResource(R.xml.preferences, s)
	}
}