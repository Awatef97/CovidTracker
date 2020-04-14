package iti.intake40.covidtracker.db.localDatabase

import androidx.lifecycle.LiveData
import androidx.room.*
import iti.intake40.covidtracker.db.model.CovidCountryModel
import iti.intake40.covidtracker.db.model.CovidModel


@Dao
interface CovidDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(covidModel: CovidModel)
    @Query("SELECT * FROM countries_stat  ORDER BY countryName ASC ")
     fun getDataRoom(): LiveData<List<CovidModel>>
    @Query("DELETE FROM countries_stat")
    suspend fun delete()


}