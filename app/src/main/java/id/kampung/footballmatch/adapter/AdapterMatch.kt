package id.kampung.footballmatch.adapter

import android.content.Context
import android.content.Intent
import android.provider.CalendarContract
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import id.kampung.footballmatch.R
import id.kampung.footballmatch.data.model.EventModel
import kotlinx.android.synthetic.main.list_item.view.*
import org.jetbrains.anko.layoutInflater
import java.util.*

class AdapterMatch(private var match: List<EventModel>, private val context: Context, private val itemListener: OnClickListener, private val alarm: Boolean) : RecyclerView.Adapter<AdapterMatch.ViewHolder>(),
        Filterable {

    private var listFiltered: List<EventModel> = match

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(context.layoutInflater.inflate(R.layout.list_item, p0, false))
    }

    override fun getItemCount(): Int {
        return listFiltered.size
    }

    override fun onBindViewHolder(holder: ViewHolder, p1: Int) {
        val data = listFiltered[p1]

        if (alarm) {
            holder.imageAlarm.visibility = View.VISIBLE
            holder.imageAlarm.setOnClickListener {
                val intent = Intent(Intent.ACTION_EDIT)
                intent.type = "vnd.android.cursor.item/event"
                intent.putExtra(CalendarContract.Events.TITLE, "${data.strHomeTeam} vs ${data.strAwayTeam}")
                val dateStart = data.toDate()
                if (dateStart != null) {
                    intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,
                            dateStart.time)
                    val cal: Calendar = Calendar.getInstance()
                    cal.time = dateStart
                    cal.add(Calendar.MINUTE, 90)

                    intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,
                            cal.time.time)
                    intent.putExtra(CalendarContract.Events.ALL_DAY, false)
                } else {
                    intent.putExtra(CalendarContract.Events.ALL_DAY, true)
                }
                intent.putExtra(CalendarContract.Events.DESCRIPTION, data.toFormattedDate())
                context.startActivity(intent)
            }
        } else
            holder.imageAlarm.visibility = View.GONE

        holder.tvDate.text = data.toFormattedDate()
        holder.tvAwayName.text = data.strAwayTeam
        holder.tvHomeName.text = data.strHomeTeam

        if (data.intHomeScore.isNullOrEmpty()) {
            holder.tvHomeScore.text = "?"
        } else
            holder.tvHomeScore.text = data.intHomeScore

        if (data.intAwayScore.isNullOrEmpty()) {
            holder.tvAwayScore.text = "?"
        } else
            holder.tvAwayScore.text = data.intAwayScore

        holder.cardView.setOnClickListener {
            itemListener.onClick(data)
        }

    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): Filter.FilterResults {

                val charString = charSequence.toString()

                if (charString.isEmpty()) {
                    listFiltered = match
                } else {
                    val filteredList = arrayListOf<EventModel>()
                    for (row in match) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.strHomeTeam.toLowerCase().contains(charString.toLowerCase()) ||
                                row.strAwayTeam.contains(charString)) {

                            filteredList.add(row)
                        }
                    }

                    listFiltered = filteredList
                }

                val filterResults = Filter.FilterResults()
                filterResults.values = listFiltered
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: Filter.FilterResults) {
                listFiltered = filterResults.values as List<EventModel>
                notifyDataSetChanged()
            }
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tvDate: TextView = view.tvDate
        var tvHomeName: TextView = view.tvHomeName
        var tvHomeScore: TextView = view.tvHomeScore
        var tvAwayName: TextView = view.tvAwayName
        var tvAwayScore: TextView = view.tvAwayScore
        var cardView: CardView = view.cardView
        var imageAlarm: ImageView = view.imageAlarm
    }

    interface OnClickListener {
        fun onClick(item: EventModel)
    }
}