package iti.intake40.covidtracker.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import iti.intake40.covidtracker.R
import iti.intake40.covidtracker.RefreshDataWorker
import iti.intake40.covidtracker.data.CovidClient
import iti.intake40.covidtracker.model.CovidModel
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.TimeUnit

class CovidActivity : AppCompatActivity() {
    var dataList = ArrayList<CovidModel>()
    lateinit var recyclerView: RecyclerView
    lateinit var adapter:CovidAdapter
    val workManager = WorkManager.getInstance()
    val saveRequest =
        PeriodicWorkRequestBuilder<RefreshDataWorker>(15, TimeUnit.MINUTES)
            .build()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_covid)



        workManager.enqueue(saveRequest)
        workManager.enqueueUniquePeriodicWork(
            RefreshDataWorker.work,
            ExistingPeriodicWorkPolicy.REPLACE,saveRequest )
        workManager.getWorkInfosForUniqueWorkLiveData(
            RefreshDataWorker.work)
        recyclerView = findViewById(R.id.recycle_view)

        recyclerView.adapter= CovidAdapter(dataList,this)
        recyclerView.layoutManager=LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        getData()

//        workManager.getWorkInfoByIdLiveData(saveRequest.id)
//            .observe(this@CovidActivity, Observer { workInfo ->
//                if (workInfo != null && workInfo.state == WorkInfo.State.SUCCEEDED) {
//             getData()
//                }
//            })
    }
    private fun getData() {
       val covidClient: CovidClient
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

    }
}



