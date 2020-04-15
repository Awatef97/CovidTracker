package iti.intake40.covidtracker.ui.main

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import iti.intake40.covidtracker.R
import kotlinx.android.synthetic.main.activity_bar_chart.*


class BarChartActivity : AppCompatActivity() {
    var ss: String? = null
    var yy: String? = null
    var zz: String? = null
    var xx: String? = null

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bar_chart)
         ss= intent.getStringExtra("casesTextView")
         yy = intent.getStringExtra("deathsTextView")
        xx = intent.getStringExtra("recoveredTextView")
         zz = intent.getStringExtra("newcasesTextView")


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            setBarChart()
        }
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    private fun setBarChart() {
        val entries = ArrayList<BarEntry>()
        entries.add(BarEntry(0f, ss!!.toFloat()))
        entries.add(BarEntry(2f, yy!!.toFloat()))
        entries.add(BarEntry(4f, xx!!.toFloat()))
        entries.add(BarEntry(6f, zz!!.toFloat()))


        val barDataSet = BarDataSet(entries, "Cells")

        val labels = ArrayList<String>()
        labels.add("death")
        labels.add("cases")
        labels.add("recovered")
        labels.add("New Cases")
        barChart.getXAxis().setValueFormatter( IndexAxisValueFormatter(labels))


       val data = BarData(barDataSet)

        barChart.data = data // set the data and list of lables into chart
//        barChart.setDescription()  // set the description
        barChart.description.text = "cases"

        barChart.axisRight.isEnabled = false
        barChart.xAxis.axisMaximum = 10+0.1f

//   barChart.yChartMax.times(500000.toDouble())
//        barChart.minimumHeight.times(100.toDouble())

        //barDataSet.setColors(ColorTemplate.COLORFUL_COLORS)
        barDataSet.color = resources.getColor(R.color.redAccent)

        barChart.animateY(5000)

    }
}

