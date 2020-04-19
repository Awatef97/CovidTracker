package iti.intake40.covidtracker

import android.content.Context
import iti.intake40.covidtracker.Work.WorkerNotification
import iti.intake40.covidtracker.Work.WorkerNotification.Companion.countryNewCases
import iti.intake40.covidtracker.Work.WorkerNotification.Companion.countryNewDeaths
import iti.intake40.covidtracker.Work.WorkerNotification.Companion.countryTotalCases
import iti.intake40.covidtracker.Work.WorkerNotification.Companion.countryTotalDeaths
import iti.intake40.covidtracker.Work.WorkerNotification.Companion.countryTotalRecovered
import iti.intake40.covidtracker.ui.main.SettingsActivity

object SharedPrefChecking {
    fun checkSharedPref(boolean: Boolean,str: String,context: Context) {
        if (str == "notify"){
               Notification.sendNotification(context)
            }
        if(boolean) {
             if (AppPreferences.totalCases != countryTotalCases || AppPreferences.newCases != countryNewCases || AppPreferences.totalDeathCases != countryTotalDeaths || AppPreferences.newDeathCases != countryNewDeaths || AppPreferences.recoveredCases != countryTotalRecovered) {
                AppPreferences.countryName = WorkerNotification.countryTitle
                AppPreferences.totalCases = countryTotalCases
                AppPreferences.newCases = countryNewCases
                AppPreferences.newDeathCases = countryNewDeaths
                AppPreferences.totalDeathCases = countryTotalDeaths
                AppPreferences.recoveredCases = countryTotalRecovered
                AppPreferences.isSubscribed = SettingsActivity.subscribeFlag

            }


            else {
                AppPreferences.countryName = ""
                AppPreferences.totalCases = ""
                AppPreferences.newCases = ""
                AppPreferences.newDeathCases = ""
                AppPreferences.totalDeathCases = ""
                AppPreferences.recoveredCases = ""
                AppPreferences.isSubscribed = SettingsActivity.subscribeFlag

            }

        }
    }

}