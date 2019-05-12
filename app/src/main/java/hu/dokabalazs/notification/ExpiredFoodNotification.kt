package hu.dokabalazs.notification


import android.content.Context
import androidx.work.*
import java.util.concurrent.TimeUnit


object ExpiredFoodNotification {
	private fun build(context: Context, minutes: Long) {
		ExpiredFoodNotificationWorker.initNotifications(context = context)

		val notificationBuilder = PeriodicWorkRequest.Builder(
			ExpiredFoodNotificationWorker::class.java,
			minutes,
			TimeUnit.MINUTES
		)

		val notificationConstraints = Constraints.Builder().apply {
			setRequiredNetworkType(NetworkType.CONNECTED)
			setRequiresBatteryNotLow(true)
		}.build()

		val data = Data.Builder().build()

		val work = notificationBuilder.apply {
			setInputData(data)
			setConstraints(notificationConstraints)
		}.build()

		WorkManager.getInstance().enqueue(work)
	}

	fun setNotifications(context: Context, show: Boolean) {
		if (show)
			build(context, 15)
		else
			WorkManager.getInstance().cancelAllWork()
	}

	fun setNotificationFrequency(context: Context, freq: Long) {
		WorkManager.getInstance().cancelAllWork()
		// Prefs.putString("pref_freq", freq.toString())
		build(context, 15)
	}
}