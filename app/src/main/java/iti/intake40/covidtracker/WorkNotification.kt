package iti.intake40.covidtracker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.provider.SyncStateContract.Helpers.insert
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.work.ListenableWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import iti.intake40.covidtracker.db.model.CovidCountryModel
import iti.intake40.covidtracker.db.remoteDatabase.NotificationObject
import iti.intake40.covidtracker.ui.main.CovidAdapter
import iti.intake40.covidtracker.ui.main.CovidViewModel
import iti.intake40.covidtracker.ui.main.SettingsActivity
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class WorkerNotification(context: Context, workerParams: WorkerParameters) : Worker(context,
    workerParams
) {
    var notificationId = Random().nextInt(10000)
    var title: String? = ""
    var countryTitle: String? =null
    var countryTotalCases: String? =null
    var countryNewCases: String? =null
    var countryTotalDeaths: String? =null
    var countryNewDeaths: String? =null
    var countryTotalRecovered : String? =null


    private lateinit var covidViewModel: CovidViewModel
    companion object {
        val work = "workNotification"

    }


    override fun doWork(): ListenableWorker.Result {
        SettingsActivity.countryName?.let { getCountryData(it) }
        return ListenableWorker.Result.success()
    }

    fun sendNotification() {
        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "WorkManager_00"

        //If on Oreo then notification required a notification channel.
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "WorkManager",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(applicationContext, channelId)
            .setContentTitle(countryTitle)
             .setStyle(NotificationCompat.BigTextStyle().bigText("New Cases "+ countryNewCases + "\nNew Deaths " + countryNewDeaths +"\nTotal Cases " + countryTotalCases + "\nTotal Deaths "+ countryTotalDeaths + "\nTotal Recovered " + countryTotalRecovered))
            .setSmallIcon(R.drawable.ic_launcher_background)


        notificationManager.notify(notificationId, notification.build())
    }

fun getCountryData(country:String) {

    val call: Call<ResponseBody> = NotificationObject.getNotificationClient.getRowData(country)
    call.enqueue(object : Callback<ResponseBody> {

        override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {

            val obj = JSONObject(response!!.body()!!.string())

            val arr: JSONArray = obj.getJSONArray("latest_stat_by_country")

            val size = arr.length()
            Log.d("success",size.toString())
            for (i in 0 until  1) {
                val detail = arr.getJSONObject(i)

                val model = CovidCountryModel(
                    detail.getString("country_name"), detail.getString("total_cases"),
                    detail.getString("new_cases"), detail.getString("total_deaths"),
                    detail.getString("new_deaths"),detail.getString("total_recovered")
                )


                countryTitle = model.countryName

                if (model.newCases == "") {
                    countryNewCases = "0"
                } else {
                    countryNewCases = model.newCases
                }
                if (model.totalDeaths == "") {
                    countryTotalDeaths = "0"
                } else {
                    countryTotalDeaths = model.totalDeaths
                }
                if (model.totalCases == "") {
                    countryTotalCases = "0"
                } else {
                    countryTotalCases = model.totalCases

                }
                if (model.newDeaths == "") {
                    countryNewDeaths = "0"
                } else {
                    countryNewDeaths = model.newDeaths
                }

                if (model.totalRecovered == "") {
                    countryTotalRecovered = "0"
                } else {
                    countryTotalRecovered = model.totalRecovered
                }

                    sendNotification()

            }

        }


        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
            Log.d("fail","failed")
        }
    })


}

}