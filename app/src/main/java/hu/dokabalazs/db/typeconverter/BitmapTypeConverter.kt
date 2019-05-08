package hu.dokabalazs.db.typeconverter

import android.R
import android.arch.persistence.room.TypeConverter
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory


class BitmapTypeConverter {
	companion object {
		var context: Context? = null
	}

	@TypeConverter
	fun toBitmap(value: String?): Bitmap? {
		return BitmapFactory.decodeResource(context?.resources, R.drawable.ic_menu_camera)
	}

	@TypeConverter
	fun fromBitmap(value: Bitmap?): String? {
		return ""
	}
}