package iti.intake40.covidtracker

import android.app.Application
import android.os.Build
import android.util.Log
import androidx.work.*
import iti.intake40.covidtracker.data.CovidClient


import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

import java.util.concurrent.TimeUnit

/**
 * Override application to setup background work via WorkManager
 */
class  CovidTrackerApplication : Application() {



}
