package iti.intake40.covidtracker.sharedpref

import android.content.Context
import iti.intake40.covidtracker.notification.Notification
import iti.intake40.covidtracker.work.Work
import iti.intake40.covidtracker.work.Work.Companion.countryNewCases
import iti.intake40.covidtracker.work.Work.Companion.countryNewDeaths
import iti.intake40.covidtracker.work.Work.Companion.countryTotalCases
import iti.intake40.covidtracker.work.Work.Companion.countryTotalDeaths
import iti.intake40.covidtracker.work.Work.Companion.countryTotalRecovered
import iti.intake40.covidtracker.sharedpref.AppPreferences
import iti.intake40.covidtracker.ui.main.SettingsActivity
import iti.intake40.covidtracker.ui.main.SettingsActivity.Companion.subscribeFlag

object SharedPrefChecking {
    fun putDataInSharedPref(boolean: Boolean, str: String, context: Context) {
        if (str == "notify") {
            Notification.sendNotification(context)
        }
        if (boolean == true) {
            when (str) {
                "notify" ->

                    if (AppPreferences.totalCases != countryTotalCases || AppPreferences.newCases != countryNewCases || AppPreferences.totalDeathCases != countryTotalDeaths || AppPreferences.newDeathCases != countryNewDeaths || AppPreferences.recoveredCases != countryTotalRecovered) {
                        AppPreferences.countryName = Work.countryTitle
                        AppPreferences.totalCases = countryTotalCases
                        AppPreferences.newCases = countryNewCases
                        AppPreferences.newDeathCases = countryNewDeaths
                        AppPreferences.totalDeathCases = countryTotalDeaths
                        AppPreferences.recoveredCases = countryTotalRecovered
                        AppPreferences.isSubscribed = SettingsActivity.subscribeFlag
                    }
                "Set From Shared Pref" ->
                    if (AppPreferences.totalCases != Work.countryTotalCases || AppPreferences.newCases != Work.countryNewCases || AppPreferences.totalDeathCases != Work.countryTotalDeaths || AppPreferences.newDeathCases != Work.countryNewDeaths || AppPreferences.recoveredCases != Work.countryTotalRecovered) {
                        Work.countryTitle = AppPreferences.countryName
                        countryTotalCases = AppPreferences.totalCases
                        countryNewCases = AppPreferences.newCases
                        countryNewDeaths = AppPreferences.newDeathCases
                        countryTotalDeaths = AppPreferences.totalDeathCases
                        countryTotalRecovered = AppPreferences.recoveredCases
                        subscribeFlag = AppPreferences.isSubscribed

                    }
            }
        } else {
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









