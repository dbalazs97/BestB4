package hu.dokabalazs

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import hu.dokabalazs.adapter.FoodAdapter
import hu.dokabalazs.model.Food
import kotlinx.android.synthetic.main.activity_main.*
import java.time.LocalDate
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        food_item_list.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = FoodAdapter(arrayOf(
                Food(1, "Milk", "10 litres", GregorianCalendar( 2019,5,10).time),
                Food(1, "Eggs", "15", GregorianCalendar(2019,10,15).time),
                Food(1, "Salami", "750 grammes", GregorianCalendar(2020,6,27).time),
                Food(1, "Salami", "750 grammes", GregorianCalendar(2020,6,27).time),
                Food(1, "Salami", "750 grammes", GregorianCalendar(2020,6,27).time),
                Food(1, "Salami", "750 grammes", GregorianCalendar(2020,6,27).time),
                Food(1, "Salami", "750 grammes", GregorianCalendar(2020,6,27).time),
                Food(1, "Salami", "750 grammes", GregorianCalendar(2020,6,27).time),
                Food(1, "Salami", "750 grammes", GregorianCalendar(2020,6,27).time),
                Food(1, "Salami", "750 grammes", GregorianCalendar(2020,6,27).time),
                Food(1, "Salami", "750 grammes", GregorianCalendar(2020,6,27).time),
                Food(1, "Salami", "750 grammes", GregorianCalendar(2020,6,27).time),
                Food(1, "Spread Butter", "500 grammes", GregorianCalendar(2021,2,11).time)
            ))
        }
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
}
