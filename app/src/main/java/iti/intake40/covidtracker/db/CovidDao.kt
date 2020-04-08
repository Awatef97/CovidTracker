package iti.intake40.covidtracker.db

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import iti.intake40.covidtracker.db.model.CovidModel
import iti.intake40.covidtracker.db.model.ID


@Dao
interface CovidDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(covidModel: CovidModel)
    @Query("SELECT * FROM countries_stat")
     fun getDataRoom(): LiveData<List<CovidModel>>


}