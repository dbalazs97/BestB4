package hu.dokabalazs.notification


import android.content.Context
import androidx.work.*
import java.util.concurrent.TimeUnit


object ExpiredFoodNotification {
	private fun build(context: Context, days: Long) {
		ExpiredFoodNotificationWorker.initNotifications(context = context)

		val notificationBuilder = PeriodicWorkRequest.Builder(
			ExpiredFoodNotificationWorker::class.java,
			days,
			TimeUnit.DAYS
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

	fun enableNotifications(context: Context) {
		build(context, 1)
	}
}