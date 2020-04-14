package iti.intake40.covidtracker.db.remoteDatabase

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NotificationObject {
    var BASE_URL: String = "https://coronavirus-monitor.p.rapidapi.com/coronavirus/"
    val getNotificationClient: CovidInterface
        get() {

            val gson = GsonBuilder()
                .setLenient()
                .create()

            val interceptor = HttpLoggingInterceptor()
            val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

            return retrofit.create(CovidInterface::class.java)
        }
}