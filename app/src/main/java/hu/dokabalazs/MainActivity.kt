package hu.dokabalazs

import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import hu.dokabalazs.db.FoodDatabase
import hu.dokabalazs.db.typeconverter.BitmapTypeConverter
import hu.dokabalazs.util.FragmentStore
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*


class MainActivity : AppCompatActivity() {

	private lateinit var currentFragment: Fragment

	companion object {
		const val REQUEST_IMAGE_CAPTURE = 1
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		setSupportActionBar(toolbar)

		// Initialize the database with a context
		FoodDatabase.invoke(this@MainActivity)

		BitmapTypeConverter.context = this

		FragmentStore.foodListFragment.onItemClickListener = {
			changeFragment(FragmentStore.foodDetailFragment(it), backStack = true)
		}
		changeFragment(FragmentStore.foodListFragment, backStack = false)

		fab.setOnClickListener { changeFragment(FragmentStore.qrScannerFragment, backStack = true) }
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

	fun showFab() {
		fab.show()
	}

	fun changeFragment(fragment: Fragment, backStack: Boolean = true) {

		when {
			FragmentStore.fabState[fragment] ?: false -> fab.show()
			!(FragmentStore.fabState[fragment] ?: false) -> fab.hide()
		}

		Handler().post {
			currentFragment = fragment
			val fragmentTransaction = supportFragmentManager.beginTransaction()

			fragmentTransaction.apply {
				if (backStack) addToBackStack(null)
				replace(R.id.fragment_container, currentFragment)
			}.commit()
			supportFragmentManager.executePendingTransactions()
		}
	}
}
