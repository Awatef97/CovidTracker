package iti.intake40.covidtracker.work

import android.content.Context
import android.util.Log
import androidx.work.ListenableWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import iti.intake40.covidtracker.sharedpref.AppPreferences
import iti.intake40.covidtracker.sharedpref.SharedPrefChecking
import iti.intake40.covidtracker.db.model.CovidCountryModel
import iti.intake40.covidtracker.db.remoteDatabase.CovidClient
import iti.intake40.covidtracker.ui.main.SettingsActivity
import iti.intake40.covidtracker.ui.main.SettingsActivity.Companion.subscribeFlag
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class Work(context: Context, workerParams: WorkerParameters) : Worker(context,
    workerParams
) {

    var notificationId = Random().nextInt(10000)
    companion object {
        val work = "workNotification"
        var countryTitle: String? = null
        var countryTotalCases: String? = null
        var countryNewCases: String? = null
        var countryTotalDeaths: String? = null
        var countryNewDeaths: String? = null
        var countryTotalRecovered: String? = null

    }


    override fun doWork(): ListenableWorker.Result {
      checkCountryName(AppPreferences.countryName)
        return ListenableWorker.Result.success()
    }


    fun getCountryData(country: String) {
        val call: Call<ResponseBody> = CovidClient.getClient.getRowData(country)
        call.enqueue(object : Callback<ResponseBody> {

            override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {

                val obj = JSONObject(response!!.body()!!.string())

                val arr: JSONArray = obj.getJSONArray("latest_stat_by_country")

                val size = arr.length()
                Log.d("success", size.toString())
                for (i in 0 until 1) {
                    val detail = arr.getJSONObject(i)

                    val model = CovidCountryModel(
                        detail.getString("country_name"), detail.getString("total_cases"),
                        detail.getString("new_cases"), detail.getString("total_deaths"),
                        detail.getString("new_deaths"), detail.getString("total_recovered")
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

                    SharedPrefChecking.putDataInSharedPref(subscribeFlag,"notify",applicationContext)



                }
            }


            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.d("fail", "failed")
            }
        })


    }


    fun checkCountryName(str: String?) {
        when (str) {
            "" -> SettingsActivity.countryName?.let { getCountryData(it) }


            else ->  AppPreferences.countryName?.let { getCountryData(it) }

            }

        }
    }

