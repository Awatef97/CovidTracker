package iti.intake40.covidtracker.ui.main

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import iti.intake40.covidtracker.sharedpref.AppPreferences
import iti.intake40.covidtracker.R
import iti.intake40.covidtracker.sharedpref.SharedPrefChecking
import iti.intake40.covidtracker.db.model.CovidModel
import iti.intake40.covidtracker.network.Network
//import iti.intake40.covidtracker.ui.main.SettingsActivity.Companion.checkSharedPref
import kotlinx.android.synthetic.main.activity_covid.*


class CovidActivity : AppCompatActivity() {
    private lateinit var covidViewModel: CovidViewModel
    lateinit var recyclerView: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        AppPreferences.init(this)
        setContentView(R.layout.activity_covid)

        SharedPrefChecking.putDataInSharedPref(
            AppPreferences.isSubscribed,"Set From Shared Pref",applicationContext)


        recyclerView = findViewById(R.id.recycle_view)

        covidViewModel = ViewModelProvider(this).get(CovidViewModel::class.java)

        covidViewModel.alldata.observe(this, Observer { covids ->

            // Update the cached copy of the words in the adapter.
            covids?.let {
                search_view.setVisibility(View.VISIBLE)

                recyclerView.adapter = CovidAdapter(it as MutableList<CovidModel>, this)

                recyclerView.layoutManager =
                    LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                search(search_view, recyclerView.adapter as CovidAdapter)


            }
        })



        if (covidViewModel.alldata.value.isNullOrEmpty() && !Network.checkNetworkState(
                applicationContext
            )
        ) {
            search_view.setVisibility(View.GONE)
            Toast.makeText(applicationContext, R.string.connectionToast, Toast.LENGTH_SHORT)
                .show()

            // finish()
        }

        itemsswipetorefresh.setProgressBackgroundColorSchemeColor(
            ContextCompat.getColor(
                this,
                R.color.colorPrimary
            )
        )
        itemsswipetorefresh.setColorSchemeColors(Color.WHITE)

        itemsswipetorefresh.setOnRefreshListener {


            search_view.setVisibility(View.VISIBLE)
            covidViewModel.getData()
            covidViewModel.alldata.observe(this, Observer { covids ->

                // Update the cached copy of the words in the adapter.
                covids?.let {

                    recyclerView.adapter = CovidAdapter(it as MutableList<CovidModel>, this)

                    recyclerView.layoutManager =
                        LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                    search(search_view, recyclerView.adapter as CovidAdapter)


                }
            })
            itemsswipetorefresh.isRefreshing = false

        }
        // recyclerView.adapter?.notifyDataSetChanged()
        //getData()

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.getItemId()


        if (id == R.id.action_one) {
            // if (Network.checkNetworkState(applicationContext)) {
            val intent = Intent(applicationContext, SettingsActivity::class.java)
            startActivity(intent)
            /* } else{
                     Toast.makeText(applicationContext, "check internet connection to subscribe a country to get notification ", Toast.LENGTH_SHORT)
                         .show()
                 }*/

        }

        return true
    }


    private fun search(searchView: SearchView, adapter: CovidAdapter) {

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(s: String): Boolean {
                return false
            }

            override fun onQueryTextChange(s: String): Boolean {
                adapter.getFilter().filter(s)
                //println(s)
                return true
            }
        })
    }

}