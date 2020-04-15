package iti.intake40.covidtracker.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import iti.intake40.covidtracker.R
import iti.intake40.covidtracker.db.model.CovidModel
import kotlinx.android.synthetic.main.list_item_home.view.*


class CovidAdapter(private var dataList: List<CovidModel>, private val context: Context) :
    RecyclerView.Adapter<CovidAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.list_item_home,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return dataList.size
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val covidModel = dataList.get(position)
        holder.countryTextView.text = covidModel.countryName
        holder.casesTextView.text = covidModel.activeCases
        holder.deathsTextView.text = covidModel.deaths
        holder.recoveredTextView.text = covidModel.totalRecovered
        holder.newcasesTextView.text = covidModel.newCases
    }

    internal fun setCovid(dataLists: List<CovidModel>) {
        this.dataList = dataLists
        notifyDataSetChanged()
    }

    class ViewHolder(itemLayoutView: View) : RecyclerView.ViewHolder(itemLayoutView) {
        lateinit var countryTextView: TextView
        lateinit var casesTextView: TextView
        lateinit var deathsTextView: TextView
        lateinit var recoveredTextView: TextView
        lateinit var newcasesTextView: TextView


        init {
            countryTextView = itemLayoutView.findViewById(R.id.country_name_txv)
            casesTextView = itemLayoutView.findViewById(R.id.cases_txv)
            deathsTextView = itemLayoutView.findViewById(R.id.deaths_txv)
            recoveredTextView = itemLayoutView.findViewById(R.id.total_recovered_txv)
            newcasesTextView = itemLayoutView.findViewById(R.id.new_cases_txv)
            itemView.statisticsBtn.setOnClickListener {
                adapterPosition
                Log.d("s",adapterPosition.toString())
                val intent = Intent(itemView.context, BarChartActivity::class.java);

                intent.putExtra("casesTextView",casesTextView.text)
                intent.putExtra("deathsTextView",deathsTextView.text)
                intent.putExtra("recoveredTextView",recoveredTextView.text)
                intent.putExtra("newcasesTextView",newcasesTextView.text)
                Log.d("ddddddddddd",newcasesTextView.text.toString())
                startActivity(itemView.context,intent, Bundle.EMPTY)


            }

        }


    }
}








