package iti.intake40.covidtracker

import android.content.Context
import android.content.SharedPreferences


object AppPreferences {
    private const val NAME = "SpinKotlin"
    private const val MODE = Context.MODE_PRIVATE
    private lateinit var preferences: SharedPreferences

    // list of app specific preferences
    private val Country_Name = Pair("countryName", "")
    private val Country_Total_Cases = Pair("totalCases", "")
    private val Country_New_Cases = Pair("newCases", "")
    private val Country_Total_Death_Cases = Pair("totalDeathCases", "")
    private val Country_New_Death_Cases = Pair("newDeathCases", "")
    private val Country_Recovered_Cases = Pair("recoveredCases", "")
    private val IS_Subscribed = Pair("isSubscribed", true)




    fun init(context: Context) {
        preferences = context.getSharedPreferences(NAME, MODE)
    }

    /**
     * SharedPreferences extension function, so we won't need to call edit() and apply()
     * ourselves on every SharedPreferences operation.
     */
    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = edit()
        operation(editor)
        editor.apply()
    }



    var totalCases: String?
        // custom getter to get a preference of a desired type, with a predefined default value
        get() = preferences.getString(Country_Total_Cases.first, Country_Total_Cases.second)
        // custom setter to save a preference back to preferences file
        set(value) = preferences.edit {
            it.putString(Country_Total_Cases.first, value)
        }


    var newCases: String?
        // custom getter to get a preference of a desired type, with a predefined default value
        get() = preferences.getString(Country_New_Cases.first, Country_New_Cases.second)
        // custom setter to save a preference back to preferences file
        set(value) = preferences.edit {
            it.putString(Country_New_Cases.first, value)
        }

    var totalDeathCases: String?
        // custom getter to get a preference of a desired type, with a predefined default value
        get() = preferences.getString(Country_Total_Death_Cases.first, Country_Total_Death_Cases.second)
        // custom setter to save a preference back to preferences file
        set(value) = preferences.edit {
            it.putString(Country_Total_Death_Cases.first, value)
        }

    var newDeathCases: String?
        // custom getter to get a preference of a desired type, with a predefined default value
        get() = preferences.getString(Country_New_Death_Cases.first, Country_New_Death_Cases.second)
        // custom setter to save a preference back to preferences file
        set(value) = preferences.edit {
            it.putString(Country_New_Death_Cases.first, value)
        }

    var recoveredCases: String?
        // custom getter to get a preference of a desired type, with a predefined default value
        get() = preferences.getString(Country_Recovered_Cases.first, Country_Recovered_Cases.second)
        // custom setter to save a preference back to preferences file
        set(value) = preferences.edit {
            it.putString(Country_Recovered_Cases.first, value)
        }
    var countryName: String?
        // custom getter to get a preference of a desired type, with a predefined default value
        get() = preferences.getString(Country_Name.first, Country_Name.second)
        // custom setter to save a preference back to preferences file
        set(value) = preferences.edit {
            it.putString(Country_Name.first, value)
        }

    var isSubscribed: Boolean
        // custom getter to get a preference of a desired type, with a predefined default value
        get() = preferences.getBoolean(IS_Subscribed.first, IS_Subscribed.second)

        // custom setter to save a preference back to preferences file
        set(value) = preferences.edit {
            it.putBoolean(IS_Subscribed.first, value)
        }
}