package hu.dokabalazs.notification

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import hu.dokabalazs.MainActivity
import hu.dokabalazs.R
import hu.dokabalazs.db.typeconverter.BitmapTypeConverter.Companion.context
import hu.dokabalazs.model.Food
import hu.dokabalazs.util.FragmentStore


internal class ExpiredFoodNotificationWorker(val context: Context, workerParams: WorkerParameters) :
	Worker(context, workerParams) {

	private fun prepareNotification(
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

	private fun showNotification(builder: NotificationCompat.Builder) {
		val notificationManager = NotificationManagerCompat.from(context)
		notificationManager.cancelAll()
		notificationManager.notify(Integer.parseInt(id.toString().substring(0, 6), 16), builder.build())
	}

	override fun doWork(): Result {
		val almostExpiredFood = FragmentStore.foodListFragment.foodAdapter.getAlmostExpired(1)
		val expiredFood = FragmentStore.foodListFragment.foodAdapter.getExpired()
		showNotification(prepareNotification(context, almostExpiredFood, expiredFood))
		return Result.success()
	}

	companion object {
		private const val CHANNEL_ID = "bestb4_daily_channel"
		private var pendingIntent: PendingIntent? = null

		fun initNotifications() {
			val intent = Intent(context, MainActivity::class.java)
			intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
			pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)
		}
	}
}