package hu.dokabalazs.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.v4.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import hu.dokabalazs.MainActivity
import hu.dokabalazs.R
import hu.dokabalazs.model.Food
import hu.dokabalazs.util.FragmentStore


internal class ExpiredFoodNotificationWorker(val context: Context, workerParams: WorkerParameters) :
	Worker(context, workerParams) {

	fun prepareNotification(
		context: Context,
		almostExpiredFood: List<Food>,
		expiredFood: List<Food>
	): NotificationCompat.Builder {

		val notificationLabel =
			"""
				|${context.getString(R.string.expired_food_label)} ${expiredFood.joinToString { it.name }}
				|${context.getString(R.string.almost_expired_food_label)} ${almostExpiredFood.joinToString { it.name }}
			|""".trimMargin()

		return NotificationCompat.Builder(context, CHANNEL_ID).apply {
			setSmallIcon(R.mipmap.ic_launcher)
			setContentTitle(context.getString(R.string.food_notification_title))
			setContentText(notificationLabel)
			priority = NotificationCompat.PRIORITY_DEFAULT
			setContentIntent(pendingIntent)
			setAutoCancel(true)
		}
	}

	private fun createNotificationChannel(notificationManager: NotificationManager) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			val name = "Fridge status"
			val descriptionText = "Daily reminder for fridge status"
			val importance = NotificationManager.IMPORTANCE_DEFAULT
			val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
				description = descriptionText
			}
			notificationManager.createNotificationChannel(channel)
		}
	}

	fun showNotification(context: Context, builder: NotificationCompat.Builder) {
		val notificationManager: NotificationManager =
			context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
		notificationManager.cancelAll()
		createNotificationChannel(notificationManager)
		notificationManager.notify("TAG", 16, builder.build())
	}

	override fun doWork(): Result {
		val almostExpiredFood = FragmentStore.foodListFragment.foodAdapter.getAlmostExpired(1)
		val expiredFood = FragmentStore.foodListFragment.foodAdapter.getExpired()
		showNotification(context, prepareNotification(context, almostExpiredFood, expiredFood))
		return Result.success()
	}

	companion object {
		private const val CHANNEL_ID = "bestb4_daily_channel"
		private var pendingIntent: PendingIntent? = null

		fun initNotifications(context: Context) {
			val intent = Intent(context, MainActivity::class.java)
			intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
			pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)
		}
	}
}