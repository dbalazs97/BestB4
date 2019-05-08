package hu.dokabalazs.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context
import hu.dokabalazs.db.typeconverter.BitmapTypeConverter
import hu.dokabalazs.db.typeconverter.DateTypeConverter
import hu.dokabalazs.model.Food

@Database(entities = arrayOf(Food::class), version = 1, exportSchema = false)
@TypeConverters(value = [DateTypeConverter::class, BitmapTypeConverter::class])
abstract class FoodDatabase : RoomDatabase() {
	abstract fun foodDao(): FoodDao

	companion object {
		private var instance: FoodDatabase? = null
		operator fun get(context: Context? = null): FoodDatabase {
			if (instance == null) {

				if (context == null)
					throw IllegalArgumentException("If the database instance is not present a context is needed")

				synchronized(FoodDatabase::class) {
					instance = Room.databaseBuilder(
						context.applicationContext,
						FoodDatabase::class.java,
						"food-db"
					)
						.fallbackToDestructiveMigration()
						.build()
					return instance as FoodDatabase
				}
			}
			return instance as FoodDatabase
		}
	}
}