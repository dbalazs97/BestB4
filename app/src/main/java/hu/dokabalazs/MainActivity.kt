package hu.dokabalazs

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import hu.dokabalazs.adapter.FoodAdapter
import hu.dokabalazs.db.FoodDatabase
import hu.dokabalazs.db.typeconverter.BitmapTypeConverter
import hu.dokabalazs.model.Food
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

	companion object {
		const val REQUEST_IMAGE_CAPTURE = 1
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		setSupportActionBar(toolbar)

		// Initialize the database with a context
		FoodDatabase[this@MainActivity]

		BitmapTypeConverter.context = this

		food_item_list.apply {
			setHasFixedSize(true)
			layoutManager = LinearLayoutManager(this@MainActivity)
			adapter = FoodAdapter(
				listOf(
					Food(1, "Milk", "10 litres", GregorianCalendar(2019, 5, 10).time, null),
					Food(1, "Eggs", "15", GregorianCalendar(2019, 10, 15).time, null),
					Food(1, "Salami", "750 grammes", GregorianCalendar(2020, 6, 27).time, null),
					Food(1, "Salami", "750 grammes", GregorianCalendar(2020, 6, 27).time, null),
					Food(1, "Salami", "750 grammes", GregorianCalendar(2020, 6, 27).time, null),
					Food(1, "Salami", "750 grammes", GregorianCalendar(2020, 6, 27).time, null),
					Food(1, "Salami", "750 grammes", GregorianCalendar(2020, 6, 27).time, null),
					Food(1, "Salami", "750 grammes", GregorianCalendar(2020, 6, 27).time, null),
					Food(1, "Salami", "750 grammes", GregorianCalendar(2020, 6, 27).time, null),
					Food(1, "Salami", "750 grammes", GregorianCalendar(2020, 6, 27).time, null),
					Food(1, "Salami", "750 grammes", GregorianCalendar(2020, 6, 27).time, null),
					Food(1, "Salami", "750 grammes", GregorianCalendar(2020, 6, 27).time, null),
					Food(1, "Spread Butter", "500 grammes", GregorianCalendar(2021, 2, 11).time, null)
				)
			)
		}

		fab.setOnClickListener { takeThumbnail() }
	}

	override fun onCreateOptionsMenu(menu: Menu): Boolean {
		menuInflater.inflate(R.menu.menu_main, menu)
		return true
	}

	override fun onOptionsItemSelected(item: MenuItem): Boolean {
		return when (item.itemId) {
			R.id.action_settings -> true
			else -> super.onOptionsItemSelected(item)
		}
	}

	private fun takeThumbnail() {
		Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
			takePictureIntent.resolveActivity(packageManager)?.also {
				startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
			}
		}
	}

	override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
		if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
			val imageBitmap = data?.extras?.get("data") as Bitmap
			((food_item_list as RecyclerView).adapter as FoodAdapter).setImage(0, imageBitmap)
		}
	}
}
