package iti.intake40.covidtracker.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import iti.intake40.covidtracker.R
import iti.intake40.covidtracker.db.model.CovidModel
import kotlinx.android.synthetic.main.list_item_home.view.*
import java.util.*
import kotlin.collections.ArrayList

class CovidAdapter(private var dataList: MutableList<CovidModel>, private val context: Context) : RecyclerView.Adapter<CovidAdapter.ViewHolder>(),Filterable
{
    lateinit var  dataCopy: MutableList<CovidModel>
    init {
        dataCopy = ArrayList(dataList)
    }
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
        val covidModel=dataList.get(position)

        holder.countryTextView.text=covidModel.countryName
        holder.casesTextView.text=covidModel.activeCases
        holder.deathsTextView.text=covidModel.deaths
        holder.recoveredTextView.text=covidModel.totalRecovered
        holder.newcasesTextView.text=covidModel.newCases
    }


    internal fun setCovid(dataLists: MutableList<CovidModel>) {
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
            countryTextView=itemLayoutView.findViewById(R.id.country_name_txv)
            casesTextView=itemLayoutView.findViewById(R.id.cases_txv)
            deathsTextView=itemLayoutView.findViewById(R.id.deaths_txv)
            recoveredTextView=itemLayoutView.findViewById(R.id.total_recovered_txv)
            newcasesTextView=itemLayoutView.findViewById(R.id.new_cases_txv)
            itemView.statisticsBtn.setOnClickListener {
                adapterPosition

                val intent = Intent(itemView.context, BarChartActivity::class.java);
                val cases =  casesTextView.text.toString().replace(",", "")
                val deathCases = deathsTextView.text.toString().replace(",", "")
                val recoveredCases = recoveredTextView.text.toString().replace(",", "")
                val newCases = newcasesTextView.text.toString().replace(",", "")
                intent.putExtra("casesTextView",cases)
                intent.putExtra("deathsTextView",deathCases)
                intent.putExtra("recoveredTextView",recoveredCases)
                intent.putExtra("newcasesTextView",newCases)
                startActivity(itemView.context,intent, Bundle.EMPTY)


            }


        }
    }

    /**searching in the recyclerView */
    override fun getFilter(): Filter {
        return dataFilter
    }

    val dataFilter = object : Filter() {
        override fun performFiltering(charSequence: CharSequence?): Filter.FilterResults {

            val filteredData = java.util.ArrayList<CovidModel>()

            if (charSequence == null || charSequence.length == 0) {
                filteredData.addAll(dataCopy)
            } else {
                val filterPattern = charSequence.toString().toLowerCase().trim { it <= ' ' }

                for (covid in dataCopy) {
                    if (covid.countryName.toLowerCase().contains(filterPattern)) {
                        filteredData.add(covid)
                    }
                }
            }


            val filterResults = Filter.FilterResults()
            filterResults.values = filteredData
            return filterResults
        }


        override fun publishResults(
            charSequence: CharSequence,
            filterResults: Filter.FilterResults
        ) {
            dataList.clear()
            dataList.addAll(filterResults.values as List<CovidModel>)
            println(dataList)
            setCovid(dataList)

        }
    }
}
/*








}*/
