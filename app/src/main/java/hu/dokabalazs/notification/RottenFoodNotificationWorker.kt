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


internal class PlayerOnlineNotificationWorker(context: Context, workerParams: WorkerParameters) :
	Worker(context, workerParams) {

	private fun prepareNotification(
		context: Context,
		almostRottenFood: List<Food>,
		rottenFood: List<Food>
	): NotificationCompat.Builder {
		return NotificationCompat.Builder(context, CHANNEL_ID).apply {
			setSmallIcon(R.mipmap.ic_launcher)
			setContentTitle(context.getString(R.string.food_notification_title))
			setContentText("")
			priority = NotificationCompat.PRIORITY_DEFAULT
			setContentIntent(pendingIntent)
			setAutoCancel(true)
		}
	}

	private fun showNotification(builder: NotificationCompat.Builder) {
		val notificationManager = NotificationManagerCompat.from(context!!)
		notificationManager.cancelAll()
		notificationManager.notify(Integer.parseInt(id.toString().substring(0, 6), 16), builder.build())
	}

	override fun doWork(): Result {
		return Result.success()
	}

	companion object {
		private val CHANNEL_ID = "channel0"
		private var pendingIntent: PendingIntent? = null

		fun initNotifications() {
			val intent = Intent(context, MainActivity::class.java)
			intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
			pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)
		}
	}
}