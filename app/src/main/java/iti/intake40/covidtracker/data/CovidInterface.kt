package iti.intake40.covidtracker.data
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

interface CovidInterface {
        @Headers(	"x-rapidapi-key: 32e11f6438msh0927f85fa4b0c97p1fd429jsn8b1c7bd60777")
        @GET("cases_by_country.php")
        fun getData(): Call<ResponseBody>


}