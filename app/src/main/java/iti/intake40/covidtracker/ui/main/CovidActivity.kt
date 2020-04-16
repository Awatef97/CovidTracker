package iti.intake40.covidtracker.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import iti.intake40.covidtracker.R
import iti.intake40.covidtracker.db.model.CovidModel
import iti.intake40.covidtracker.network.Network
import kotlinx.android.synthetic.main.activity_covid.*


class CovidActivity : AppCompatActivity() {
    private lateinit var covidViewModel: CovidViewModel
   // lateinit var searchView: SearchView
    // lateinit var covidViewModel:CovidViewModel
   // var dataList = ArrayList<CovidModel>()
    lateinit var recyclerView: RecyclerView
           var flag=true
   // var adapter = CovidAdapter(dataList , this)
    override fun onCreate(savedInstanceState: Bundle?) {
        //covidViewModel = ViewModelProviders.of(this).get(covidViewModel!!::class.java)
        super.onCreate(savedInstanceState)
        //searchView = findViewById(R.id.search_view)

        setContentView(R.layout.activity_covid)
        recyclerView = findViewById(R.id.recycle_view)

        covidViewModel = ViewModelProvider(this).get(CovidViewModel::class.java)
      //while (flag) {

       if (covidViewModel.alldata.value == null && !Network.checkNetworkState(applicationContext)) {
           search_view.setVisibility(View.GONE)
           Toast.makeText(applicationContext, "افتح النت ي حلوووووووووووف", Toast.LENGTH_SHORT)
               .show()
           finish()
       }
       //}
       covidViewModel.alldata.observe(this, Observer { covids ->

           // Update the cached copy of the words in the adapter.
           covids?.let {

               recyclerView.adapter = CovidAdapter(it as MutableList<CovidModel>, this)

               recyclerView.layoutManager =
                   LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
               search(search_view, recyclerView.adapter as CovidAdapter)


           }
       })



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
            val intent = Intent(applicationContext, SettingsActivity::class.java)
            startActivity(intent)
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
