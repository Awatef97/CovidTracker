package iti.intake40.covidtracker.ui.main

import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.hbb20.CountryCodePicker
import iti.intake40.covidtracker.R
import iti.intake40.covidtracker.WorkerNotification
import kotlinx.android.synthetic.main.activity_settings.*
import java.util.Locale.US
import java.util.concurrent.TimeUnit


class SettingsActivity : AppCompatActivity(), CountryCodePicker.OnCountryChangeListener  {
    companion object {
        var countryName : String? = null

    }
    val workManager = WorkManager.getInstance()

    private var ccp:CountryCodePicker?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_settings)
      val params = window.attributes
        window.setLayout(1200, 1400)

        ccp = findViewById(R.id.country_code_picker)
        ccp!!.setOnCountryChangeListener(this)
        val oneHourBtn = findViewById(R.id.oneHour) as RadioButton
        val twoHoursBtn = findViewById(R.id.twoHours) as RadioButton
        val fiveHoursBtn = findViewById(R.id.fiveHours) as RadioButton
        val oneDayBtn = findViewById(R.id.oneDay) as RadioButton
        val noneBtn = findViewById(R.id.noNotification) as RadioButton
        val confirmBtn = findViewById(R.id.confirmationBtn) as Button


        twoHoursBtn.setChecked(true);

        confirmBtn.setOnClickListener {
            if (countryName != null) {

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
                } else if (noneBtn.isChecked) {
                    workManager.cancelAllWork()
                    finish()
                }
                else if (countryName == null) {
                    finish()
                }

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
