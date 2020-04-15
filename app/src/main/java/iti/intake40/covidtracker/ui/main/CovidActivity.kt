package iti.intake40.covidtracker.ui.main


import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import iti.intake40.covidtracker.R
import iti.intake40.covidtracker.WorkerNotification
import java.util.concurrent.TimeUnit


class CovidActivity : AppCompatActivity() {

    private lateinit var covidViewModel: CovidViewModel
    lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_covid)
        recyclerView = findViewById(R.id.recycle_view)
        covidViewModel = ViewModelProvider(this).get(CovidViewModel::class.java)
        covidViewModel.getData()
        covidViewModel.alldata.observe(this, Observer { covids ->

            // Update the cached copy of the words in the adapter.
            covids?.let {
                recyclerView.adapter = CovidAdapter(it, this)
                recyclerView.layoutManager =
                    LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            }
        })

    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.getItemId()
        if (id == R.id.action_one) {
            val intent = Intent(applicationContext, SettingsActivity::class.java)
            startActivity(intent)
        }
            return true
        }



}




