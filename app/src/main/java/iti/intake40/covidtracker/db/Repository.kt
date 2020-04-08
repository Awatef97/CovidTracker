package iti.intake40.covidtracker.db

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.RoomDatabase
import iti.intake40.covidtracker.db.model.CovidModel

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
class Repository(private val covidDao: CovidDao) {

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    val alldata: LiveData<List<CovidModel>> = covidDao.getDataRoom()


    suspend fun insert(covidModel: CovidModel) {
        covidDao.insert(covidModel)
        println(alldata)

    }

}