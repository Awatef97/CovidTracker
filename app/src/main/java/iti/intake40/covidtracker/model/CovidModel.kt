package iti.intake40.covidtracker.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CovidModel(
    @Expose
    @SerializedName("active_cases")
    val activeCases: String,

    @Expose
    @SerializedName("country_name")
    val countryName : String,

    @Expose
    @SerializedName("new_cases")
    val newCases : String,

    @Expose
    @SerializedName("deaths")
    val deaths : String,

   /* @Expose
    @SerializedName("region")
    val region : String,*/

    @Expose
    @SerializedName("total_recovered")
    val totalRecovered : String

    /*@Expose
    @SerializedName("new_deaths")
    val newDeaths : String*/



)
