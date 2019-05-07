package hu.dokabalazs.db

import android.arch.persistence.room.*
import hu.dokabalazs.model.Food

@Dao
interface FoodDao {
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	fun insert(food: Food)

	@Delete
	fun deleteAll(vararg foods: Food)

	@Query("SELECT * FROM food ORDER BY expiryDate ASC")
	fun getAllFoods(): List<Food>
}