package hu.dokabalazs.preferences

import com.chibatching.kotpref.KotprefModel

object Settings : KotprefModel() {
	override val kotprefName: String = "${context.packageName}_preferences"
	val notifications by booleanPref(true)
	val notify_before by stringPref(default = "1")
}