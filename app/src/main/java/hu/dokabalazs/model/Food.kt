package hu.dokabalazs.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.io.Serializable
import java.util.*

@Entity(tableName = "Food")
data class Food(
	@PrimaryKey(autoGenerate = true)
    val id: Int?,
	val name: String,
	val quantity: String,
	val insertDate: Date,
	val expiryDate: Date,
	var thumbnail: Int
) : Serializable