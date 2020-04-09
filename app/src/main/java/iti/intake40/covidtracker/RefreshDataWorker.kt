package iti.intake40.covidtracker

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import iti.intake40.covidtracker.db.model.CovidModel
import iti.intake40.covidtracker.ui.main.CovidViewModel


class RefreshDataWorker(context: Context, workerParams: WorkerParameters) : Worker(context,
    workerParams
){
    private lateinit var covidViewModel: CovidViewModel
    companion object {
        const val work = "hi"
    }
    override fun doWork(): Result {


//        covidViewModel.getData()

        Log.d(work,"upload photooooooooooooo" )

        return Result.success()

    }


}