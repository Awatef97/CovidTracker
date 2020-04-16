package iti.intake40.covidtracker.ui.main

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import iti.intake40.covidtracker.R
import kotlinx.android.synthetic.main.activity_bar_chart.*


class BarChartActivity : AppCompatActivity() {
    var cases: String? = null
    var deathCases: String? = null
    var newCases: String? = null
    var totalRecovered: String? = null


    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bar_chart)

         cases= intent.getStringExtra("casesTextView")
         deathCases = intent.getStringExtra("deathsTextView")
        totalRecovered = intent.getStringExtra("recoveredTextView")
         newCases = intent.getStringExtra("newcasesTextView")

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            setBarChart()
        }
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    private fun setBarChart() {
        val entries = ArrayList<BarEntry>()

        entries.add(BarEntry(0f, cases!!.toFloat()))
        entries.add(BarEntry(2f, newCases!!.toFloat()))
        entries.add(BarEntry(4f, totalRecovered!!.toFloat()))
        entries.add(BarEntry(6f, deathCases!!.toFloat()))


        val y: YAxis = barChart.getAxisLeft()
        y.setAxisMinValue(0f)

        val barDataSet = BarDataSet(entries, "Cases")

        val xAxisData = arrayOf(
            "Total cases",
            " ",
            "New Cases",
            " ",
            "Recovered Cases",
            " ",
            "Death cases"
        )




// Set the value formatter
        val xAxis: XAxis = barChart.getXAxis()
        xAxis.valueFormatter = IndexAxisValueFormatter(xAxisData)
       val data = BarData(barDataSet)

        barChart.data = data // set the data and list of lables into chart
//        barChart.setDescription()  // set the description
        barChart.description.text = " "

        barChart.axisRight.isEnabled = false
//        barChart.xAxis.axisMaximum = 10+0.1f



        //barDataSet.setColors(ColorTemplate.COLORFUL_COLORS)
        barDataSet.color = resources.getColor(R.color.redAccent)

        barChart.animateY(4000)

    }
}

