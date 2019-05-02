package hu.dokabalazs.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context
import hu.dokabalazs.db.typeconverter.DateTypeConverter
import hu.dokabalazs.model.Food

@Database(entities = arrayOf(Food::class), version = 1, exportSchema = false)
@TypeConverters(value = [DateTypeConverter::class])
abstract class FoodDatabase : RoomDatabase() {
    abstract fun foodDao(): FoodDao

    companion object {
        private var instance: FoodDatabase? = null
        fun getDatabase(context: Context): FoodDatabase? {
            if (instance == null) {
                synchronized(FoodDatabase::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        FoodDatabase::class.java,
                        "food-db"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return instance
        }
    }
}