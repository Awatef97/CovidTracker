package iti.intake40.covidtracker.ui.main

import android.app.Application
import androidx.lifecycle.*
import iti.intake40.covidtracker.db.remoteDatabase.CovidClient
import iti.intake40.covidtracker.db.localDatabase.CovidDataBase
import iti.intake40.covidtracker.db.model.CovidCountryModel
import iti.intake40.covidtracker.repository.Repository
import iti.intake40.covidtracker.db.model.CovidModel
import kotlinx.coroutines.*
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CovidViewModel(application: Application) :  AndroidViewModel(application) {
    var  dataList = MutableLiveData<CovidModel>()
    private val context: CoroutineScope = MainScope()
    private val repository: Repository
     var alldata  : LiveData<List<CovidModel>>
            init{
                val covidDao = CovidDataBase.getDatabase(application,context).covidDao()
                repository = Repository(covidDao)
                alldata = repository.alldata

            }

    fun getData(): MutableLiveData<CovidModel>{
        val call: Call<ResponseBody> = CovidClient.getClient.getData()
        call.enqueue(object : Callback<ResponseBody> {

            override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {

                val obj = JSONObject(response!!.body()!!.string())
                val arr: JSONArray = obj.getJSONArray("countries_stat")
                val size = arr.length()
                for(i in 0 until  size-1){
                    val detail = arr.getJSONObject(i)
                    val model = CovidModel(detail.getString("active_cases"),detail.getString("country_name"),
                        detail.getString("new_cases"),detail.getString("deaths"),
                        detail.getString("total_recovered"))

                    dataList.postValue(model)

                    insert(model)

                }


            }

            override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                println("bbbbbbbbbb")

            }

        })
        return dataList
    }
    fun insert(covidModel: CovidModel) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(covidModel)
    }





}