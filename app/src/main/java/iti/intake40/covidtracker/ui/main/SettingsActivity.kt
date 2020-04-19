package iti.intake40.covidtracker.ui.main

import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import androidx.work.*
import com.hbb20.CountryCodePicker
import iti.intake40.covidtracker.AppPreferences
import iti.intake40.covidtracker.R
import iti.intake40.covidtracker.Work.WorkerNotification
import java.util.concurrent.TimeUnit


class SettingsActivity : AppCompatActivity(), CountryCodePicker.OnCountryChangeListener  {
    companion object {
        var countryName : String? = null
        var subscribeFlag : Boolean = false
        fun checkSharedPref(boolean: Boolean) {
            when (boolean) {
                true -> if (AppPreferences.totalCases != WorkerNotification.countryTotalCases || AppPreferences.newCases != WorkerNotification.countryNewCases || AppPreferences.totalDeathCases != WorkerNotification.countryTotalDeaths || AppPreferences.newDeathCases != WorkerNotification.countryNewDeaths || AppPreferences.recoveredCases != WorkerNotification.countryTotalRecovered) {
                    WorkerNotification.countryTitle = AppPreferences.countryName
                    WorkerNotification.countryTotalCases = AppPreferences.totalCases
                     WorkerNotification.countryNewCases = AppPreferences.newCases
                     WorkerNotification.countryNewDeaths = AppPreferences.newDeathCases
                    WorkerNotification.countryTotalDeaths = AppPreferences.totalDeathCases
                    WorkerNotification.countryTotalRecovered =  AppPreferences.recoveredCases
                    subscribeFlag = AppPreferences.isSubscribed

                }

                else -> {
                    AppPreferences.countryName = ""
                    AppPreferences.totalCases = ""
                    AppPreferences.newCases = ""
                    AppPreferences.newDeathCases = ""
                    AppPreferences.totalDeathCases = ""
                    AppPreferences.recoveredCases = ""
                    AppPreferences.isSubscribed = subscribeFlag


                }

                    }
                        }
        }

        val workManager = WorkManager.getInstance()

    private var ccp:CountryCodePicker?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        window.setLayout(1200, 1400)
        ccp = findViewById(R.id.country_code_picker)
        ccp!!.setOnCountryChangeListener(this)
        val oneHourBtn = findViewById(R.id.oneHour) as RadioButton
        val twoHoursBtn = findViewById(R.id.twoHours) as RadioButton
        val fiveHoursBtn = findViewById(R.id.fiveHours) as RadioButton
        val oneDayBtn = findViewById(R.id.oneDay) as RadioButton
        val confirmBtn = findViewById(R.id.confirmationBtn) as Button
        if(subscribeFlag == true){
            confirmBtn.text = "UNSUBSCRIBE"
        }


        twoHoursBtn.setChecked(true)

        confirmBtn.setOnClickListener {
            if (countryName != null && confirmBtn.text == "SUBSCRIBE" ) {
                if (oneHourBtn.isChecked) {
                    workManager.cancelAllWork()
//                    sendWorkRequest(1L)
                    subscribeFlag = true

                    val saveRequest =
                        PeriodicWorkRequestBuilder<WorkerNotification>(15, TimeUnit.MINUTES)
                            .build()

                    workManager.enqueueUniquePeriodicWork(
                        WorkerNotification.work,
                        ExistingPeriodicWorkPolicy.REPLACE,
                        saveRequest
                    )
                    finish()
                } else if (twoHoursBtn.isChecked) {
                    workManager.cancelAllWork()
                    sendWorkRequest(2L)
                    finish()
                } else if (fiveHoursBtn.isChecked) {
                    workManager.cancelAllWork()
                    sendWorkRequest(5L)

                    finish()
                } else if (oneDayBtn.isChecked) {
                    workManager.cancelAllWork()
                    sendWorkRequest(24L)
                    finish()
                }
              if (countryName == null) {
                    finish()
                }
            }
            if ( confirmBtn.text == "UNSUBSCRIBE" ) {
                workManager.cancelAllWork()
                subscribeFlag = false
         checkSharedPref(subscribeFlag)
                finish()
            }

        }

    }
    override fun onCountrySelected() {
        countryName =ccp!!.selectedCountryName
        if (countryName == "United States"){
            countryName = "USA"
        }
    }

  fun sendWorkRequest(num: Long){
      subscribeFlag = true

      val saveRequest =
          PeriodicWorkRequestBuilder<WorkerNotification>(num, TimeUnit.HOURS)
              .build()

      workManager.enqueueUniquePeriodicWork(
          WorkerNotification.work,
          ExistingPeriodicWorkPolicy.REPLACE,
          saveRequest
      )


  }

}
