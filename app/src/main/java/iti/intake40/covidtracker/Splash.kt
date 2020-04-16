package iti.intake40.covidtracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.lifecycle.ViewModelProvider
import iti.intake40.covidtracker.ui.main.CovidActivity
import iti.intake40.covidtracker.ui.main.CovidViewModel

class Splash : AppCompatActivity() {
    private lateinit var covidViewModel: CovidViewModel
    private val SPLASH_TIME_OUT = 5000L
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        covidViewModel = ViewModelProvider(this).get(CovidViewModel::class.java)
        covidViewModel.getData()
        Handler().postDelayed(
            {
                val i = Intent(this@Splash, CovidActivity::class.java)
                startActivity(i)
                finish()
            }, SPLASH_TIME_OUT)
    }

}

