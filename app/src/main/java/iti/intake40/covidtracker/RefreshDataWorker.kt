package iti.intake40.covidtracker

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.work.CoroutineWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.google.gson.GsonBuilder
import iti.intake40.covidtracker.data.CovidClient
import iti.intake40.covidtracker.data.CovidInterface
import iti.intake40.covidtracker.model.CovidModel
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.*

import retrofit2.converter.gson.GsonConverterFactory

class RefreshDataWorker(context: Context, workerParams: WorkerParameters) : Worker(context,
    workerParams
){
    var dataList = ArrayList<CovidModel>()

    companion object {
        const val work = "hi"
    }
    override fun doWork(): Result {



//             repo()
        Log.d(work,"upload photooooooooooooo" )

        return Result.success()

    }

    fun repo(){

        val call: Call<ResponseBody> = CovidClient.getClient.getData()
        call.enqueue(object : Callback<ResponseBody> {

            override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {

                val obj = JSONObject(response!!.body()!!.string())
                val arr: JSONArray = obj.getJSONArray("countries_stat")
                val size = arr.length()
                for (i in 0 until size - 1) {
                    val detail = arr.getJSONObject(i)
                    val model = CovidModel(
                        detail.getString("active_cases"), detail.getString("country_name"),
                        detail.getString("new_cases"), detail.getString("deaths"),
                        detail.getString("total_recovered")
                    )
                    Log.d(work,detail.toString() )
                    dataList.add(model)
                }

            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                TODO("Not yet implemented")
            }


        })
    }
}