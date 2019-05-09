package hu.dokabalazs.util

import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList
import kotlin.collections.LinkedHashMap


private fun diff(date1: Date, date2: Date): Map<TimeUnit, Long> {

	val diffInMillies = date2.time - date1.time

	val units = ArrayList<TimeUnit>(EnumSet.allOf(TimeUnit::class.java))
	units.reverse()

	val result = LinkedHashMap<TimeUnit, Long>()
	var milliesRest = diffInMillies

	for (unit in units) {
		val diff = unit.convert(milliesRest, TimeUnit.MILLISECONDS)
		val diffInMilliesForUnit = unit.toMillis(diff)
		milliesRest -= diffInMilliesForUnit
		result[unit] = diff
	}

	return result
}

fun diffAsString(date1: Date, date2: Date): String {
	val diff = diff(date1, date2)
	return "${diff[TimeUnit.DAYS]} days ${diff[TimeUnit.HOURS]} hours"
}