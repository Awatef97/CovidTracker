package iti.intake40.covidtracker.ui.main

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.RadioButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.work.*
import com.hbb20.CountryCodePicker
import iti.intake40.covidtracker.R
import iti.intake40.covidtracker.network.Network
import iti.intake40.covidtracker.sharedpref.AppPreferences
import iti.intake40.covidtracker.sharedpref.SharedPrefChecking
import iti.intake40.covidtracker.work.Work
import kotlinx.android.synthetic.main.activity_settings.*
import org.w3c.dom.Text
import java.util.concurrent.TimeUnit


class SettingsActivity : AppCompatActivity(), CountryCodePicker.OnCountryChangeListener  {
    companion object {
        var countryName : String? = null
        var subscribeFlag : Boolean = false
      }

    val workManager = WorkManager.getInstance()
    private var ccp:CountryCodePicker?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        window.setLayout(1050, 1400)
        ccp = findViewById(R.id.country_code_picker)
        ccp!!.setOnCountryChangeListener(this)
        val countryNameText = findViewById(R.id.countryNametxt) as TextView
        val oneHourBtn = findViewById(R.id.oneHour) as RadioButton
        val twoHoursBtn = findViewById(R.id.twoHours) as RadioButton
        val fiveHoursBtn = findViewById(R.id.fiveHours) as RadioButton
        val oneDayBtn = findViewById(R.id.oneDay) as RadioButton
        val confirmBtn = findViewById(R.id.confirmationBtn) as Button
        if (AppPreferences.countryName == "") {
            countryNametxt.text = "Please Choose Country"
        }else{
            countryNametxt.text = AppPreferences.countryName
        }
        if(subscribeFlag == true){
            confirmBtn.text = "UNSUBSCRIBE"
        }else{
            confirmBtn.text = "SUBSCRIBE"
        }

        twoHoursBtn.setChecked(true)

        confirmBtn.setOnClickListener {
            if(!Network.checkNetworkState(applicationContext)&& confirmBtn.text == "SUBSCRIBE" ){
                AppPreferences.countryName = countryName
                AppPreferences.isSubscribed = true
            }
            if (countryName != null && confirmBtn.text == "SUBSCRIBE" ) {
                if (oneHourBtn.isChecked) {
                    workManager.cancelAllWork()
                    val saveRequest =
                        PeriodicWorkRequestBuilder<Work>(15, TimeUnit.MINUTES)
                            .build()

                    workManager.enqueueUniquePeriodicWork(
                        Work.work,
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
                SharedPrefChecking.putDataInSharedPref(subscribeFlag,"",applicationContext)
                confirmBtn.text == "SUBSCRIBE"
                finish()
            }

        }

    }
    override fun onCountrySelected() {
        countryName =ccp!!.selectedCountryName
        countryNametxt.text = countryName
        when(countryName) {
            "United States" -> countryName = "USA"
            "United Kingdom" -> countryName = "UK"
            "Iran, Islamic Republic Of" -> countryName = "Iran"
            "Russian Federation" -> countryName = "Russia"
            "South Korea" -> countryName = "S. Korea"
            "Czech Republic" -> countryName = "Czechia"
            "United Arab Emirates" -> countryName = "UAE"
            "Moldova, Republic Of" -> countryName = "Moldova"
            "Macedonia, The Former Yugoslav Republic Of" -> countryName = "North Macedonia"
            "Bolivia, Plurinational State Of" -> countryName = "Bolivia"

        }

    }


  fun sendWorkRequest(num: Long){
      subscribeFlag = true

      val saveRequest =
          PeriodicWorkRequestBuilder<Work>(num, TimeUnit.HOURS)
              .build()

      workManager.enqueueUniquePeriodicWork(
          Work.work,
          ExistingPeriodicWorkPolicy.REPLACE,
          saveRequest
      )


  }

}
