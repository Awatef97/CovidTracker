package iti.intake40.covidtracker.db.remoteDatabase
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface CovidInterface {
        @Headers(	"x-rapidapi-key: 32e11f6438msh0927f85fa4b0c97p1fd429jsn8b1c7bd60777")
        @GET("cases_by_country.php")
        fun getData(): Call<ResponseBody>

        @Headers("x-rapidapi-key: 9d5b6d8da2msh61997679b763d85p12cd75jsn4168e0f05f68")
        @GET("latest_stat_by_country.php")
        fun getRowData(@Query("country") country:String): Call<ResponseBody>


}