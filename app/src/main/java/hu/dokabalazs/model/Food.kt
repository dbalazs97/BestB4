package hu.dokabalazs.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*

@Entity(tableName = "Food")
data class Food(
    @PrimaryKey
    val id: Int,
    val name: String,
    val quantity: String,
    val expiryDate: Date
)