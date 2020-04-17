package iti.intake40.covidtracker.ui.main

import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import androidx.work.*
import com.hbb20.CountryCodePicker
import iti.intake40.covidtracker.R
import iti.intake40.covidtracker.Work.WorkerNotification
import java.util.concurrent.TimeUnit


class SettingsActivity : AppCompatActivity(), CountryCodePicker.OnCountryChangeListener  {
    companion object {
        var countryName : String? = null
        var subscribeFlag : Boolean = true

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
        if(subscribeFlag == false){
            confirmBtn.text = "UNSUBSCRIBE"
        }


        twoHoursBtn.setChecked(true)

        confirmBtn.setOnClickListener {
            if (countryName != null && confirmBtn.text == "SUBSCRIBE" ) {
                if (oneHourBtn.isChecked) {
                    workManager.cancelAllWork()
                    sendWorkRequest(1L)
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
                subscribeFlag = true
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
      subscribeFlag = false
//      val constraints = Constraints.Builder()
//          .setRequiredNetworkType(NetworkType.CONNECTED)
//          .apply {
//              if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                  setRequiresDeviceIdle(true)
//              }
//          }
//          .build()
      val saveRequest =
          PeriodicWorkRequestBuilder<WorkerNotification>(num, TimeUnit.HOURS)
//              .setConstraints(constraints)
              .build()

      workManager.enqueueUniquePeriodicWork(
          WorkerNotification.work,
          ExistingPeriodicWorkPolicy.REPLACE,
          saveRequest
      )
  }
}
