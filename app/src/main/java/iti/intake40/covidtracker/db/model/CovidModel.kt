package iti.intake40.covidtracker.db.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

const val ID= 0
@Entity(tableName = "countries_stat")
data class CovidModel(


    @ColumnInfo(name = "activeCases")
    @Expose
    @SerializedName("active_cases")
    val activeCases: String? =null,

    @ColumnInfo(name = "countryName")
    @Expose
    @SerializedName("country_name")
    val countryName : String,

    @ColumnInfo(name = "newCases")
    @Expose
    @SerializedName("new_cases")
    val newCases : String? =null,

    @ColumnInfo(name = "deaths")
    @Expose
    @SerializedName("deaths")
    val deaths : String? =null,

   /* @Expose
    @SerializedName("region")
    val region : String,*/

    @ColumnInfo(name = "totalRecovered")
    @Expose
    @SerializedName("total_recovered")
    val totalRecovered : String? =null

    /*@Expose
    @SerializedName("new_deaths")
    val newDeaths : String*/



){
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}
