package hu.dokabalazs

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import hu.dokabalazs.adapter.FoodAdapter
import hu.dokabalazs.db.FoodDatabase
import hu.dokabalazs.db.typeconverter.BitmapTypeConverter
import hu.dokabalazs.fragment.FoodListFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_food_list.*


class MainActivity : AppCompatActivity() {

	private var firstRun: Boolean = true
	private lateinit var currentFragment: Fragment

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

		changeFragment(FoodListFragment())

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

	private fun changeFragment(fragment: Fragment) {
		currentFragment = fragment
		val fragmentTransaction = supportFragmentManager.beginTransaction()

		fragmentTransaction.apply {
			addToBackStack(null)
			replace(R.id.fragment_container, currentFragment)
		}.commit()
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
