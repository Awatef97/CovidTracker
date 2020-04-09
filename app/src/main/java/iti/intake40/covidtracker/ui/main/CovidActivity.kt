package iti.intake40.covidtracker.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer

import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import iti.intake40.covidtracker.R
import iti.intake40.covidtracker.RefreshDataWorker
import iti.intake40.covidtracker.data.CovidClient
import iti.intake40.covidtracker.db.model.CovidModel
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.TimeUnit




class CovidActivity : AppCompatActivity() {
    private lateinit var covidViewModel: CovidViewModel

    // lateinit var covidViewModel:CovidViewModel
    var dataList = ArrayList<CovidModel>()
    lateinit var recyclerView: RecyclerView

    val workManager = WorkManager.getInstance()
    val saveRequest =
        PeriodicWorkRequestBuilder<RefreshDataWorker>(15, TimeUnit.MINUTES)
            .build()
    var adapter = CovidAdapter(dataList, this)
    override fun onCreate(savedInstanceState: Bundle?) {
        //covidViewModel = ViewModelProviders.of(this).get(covidViewModel!!::class.java)
        super.onCreate(savedInstanceState)


        setContentView(R.layout.activity_covid)



        workManager.enqueue(saveRequest)
        workManager.enqueueUniquePeriodicWork(
            RefreshDataWorker.work,
            ExistingPeriodicWorkPolicy.REPLACE, saveRequest
        )
        workManager.getWorkInfosForUniqueWorkLiveData(
            RefreshDataWorker.work
        )
        recyclerView = findViewById(R.id.recycle_view)
        recyclerView.adapter = CovidAdapter(dataList, this)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        covidViewModel = ViewModelProvider(this).get(CovidViewModel::class.java)
        covidViewModel.alldata.observe(this, Observer { covids ->
            // Update the cached copy of the words in the adapter.
            covids?.let {
                adapter.setCovid(it)
            }
        })
        covidViewModel.getData()
        recyclerView.adapter?.notifyDataSetChanged()
        //getData()

//        workManager.getWorkInfoByIdLiveData(saveRequest.id)
//            .observe(this@CovidActivity, Observer { workInfo ->
//                if (workInfo != null && workInfo.state == WorkInfo.State.SUCCEEDED) {
//             getData()
//                }
//            })
    }
}

   /* private fun getData() {
       //val covidClient: CovidClient
>>>>>>> 46cdff62e826b91fdc2f94afc28f775885de72d8:app/src/main/java/iti/intake40/covidtracker/ui/main/CovidActivity.kt
        val call: Call<ResponseBody> = CovidClient.getClient.getData()


        call.enqueue(object : Callback<ResponseBody> {

            override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {

                val obj = JSONObject(response!!.body()!!.string())
                val arr:JSONArray = obj.getJSONArray("countries_stat")
                val size = arr.length()
                for(i in 0 until  size-1){
                    val detail = arr.getJSONObject(i)
                    val model = CovidModel(detail.getString("active_cases"),detail.getString("country_name"),
                        detail.getString("new_cases"),detail.getString("deaths"),
                        detail.getString("total_recovered"))

                    dataList.add(model)
                }
                recyclerView.adapter?.notifyDataSetChanged()

//                workManager.getWorkInfoByIdLiveData(saveRequest.id)
//            .observe(this@CovidActivity, Observer { workInfo ->
//                if (workInfo != null && workInfo.state == WorkInfo.State.SUCCEEDED) {
//                    recyclerView.adapter?.notifyDataSetChanged()
//
//                }
//            })

                //dataList.addAll(response!!.body()!!)
            }

            override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                println("bbbbbbbbbb")

            }

        })
<<<<<<< HEAD:app/src/main/java/iti/intake40/covidtracker/view/CovidActivity.kt

    }
=======
    }*/



