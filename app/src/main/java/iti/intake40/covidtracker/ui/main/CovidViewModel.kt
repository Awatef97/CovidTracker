package iti.intake40.covidtracker.ui.main

import android.app.Application
import androidx.lifecycle.*
import iti.intake40.covidtracker.data.CovidClient
import iti.intake40.covidtracker.db.CovidDataBase
import iti.intake40.covidtracker.db.Repository
import iti.intake40.covidtracker.db.model.CovidModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CovidViewModel(application: Application) :  AndroidViewModel(application) {
    var  dataList = MutableLiveData<CovidModel>()
    private val repository: Repository
     var alldata  : LiveData<List<CovidModel>>
            init{
                val covidDao = CovidDataBase.getDatabase(application).covidDao()
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
                    val model = CovidModel(0,detail.getString("active_cases"),detail.getString("country_name"),
                        detail.getString("new_cases"),detail.getString("deaths"),
                        detail.getString("total_recovered"))
                  //  dataList.postValue(model)
                    dataList.value=model
                    insert(dataList.value!!)
                    //println(dataList.value!!)

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