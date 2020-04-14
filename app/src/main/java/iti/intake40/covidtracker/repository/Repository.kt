package iti.intake40.covidtracker.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import iti.intake40.covidtracker.db.localDatabase.CovidDao
import iti.intake40.covidtracker.db.model.CovidCountryModel
import iti.intake40.covidtracker.db.model.CovidModel
import iti.intake40.covidtracker.db.remoteDatabase.CovidClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
class Repository(private val covidDao: CovidDao) {

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    val alldata: LiveData<List<CovidModel>> = covidDao.getDataRoom()
    var  dataList = MutableLiveData<CovidModel>()


    suspend fun insert(covidModel: CovidModel) {
        covidDao.insert(covidModel)

    }





}