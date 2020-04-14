package iti.intake40.covidtracker.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


const val countryID = 0
@Entity(tableName = "latest_stat_by_country")
data class CovidCountryModel(

    @ColumnInfo(name = "countryName")
    @Expose
    @SerializedName("country_name")
    val countryName: String? =null,

    @ColumnInfo(name = "totalCases")
    @Expose
    @SerializedName("total_cases")
    val totalCases : String,

    @ColumnInfo(name = "newCases")
    @Expose
    @SerializedName("new_cases")
    val newCases : String? =null,

    @ColumnInfo(name = "totalDeaths")
    @Expose
    @SerializedName("total_deaths")
    val totalDeaths : String? =null,

    @ColumnInfo(name = "newDeaths")
    @Expose
    @SerializedName("new_deaths")
    val newDeaths: String? =null,

    @ColumnInfo(name = "totalRecovered")
    @Expose
    @SerializedName("total_recovered")
    val totalRecovered: String? =null)
{
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}
