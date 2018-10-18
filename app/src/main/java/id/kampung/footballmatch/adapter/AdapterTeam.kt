package id.kampung.footballmatch.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import id.kampung.footballmatch.R
import id.kampung.footballmatch.data.model.EventModel
import id.kampung.footballmatch.data.model.TeamModel
import id.kampung.footballmatch.view.team.DetailTeam
import kotlinx.android.synthetic.main.list_team.view.*
import org.jetbrains.anko.startActivity

class AdapterTeam(val context: Context, private val list: List<TeamModel>) : RecyclerView.Adapter<AdapterTeam.ViewHolder>(),
        Filterable {

    private var listFiltered: List<TeamModel> = list
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_team, p0, false))
    }

    override fun getItemCount(): Int {
        return listFiltered.size
    }

    override fun onBindViewHolder(holder: ViewHolder, i: Int) {
        val item: TeamModel = listFiltered[i]
        holder.name.text = item.strTeam
        Glide.with(context).load(item.strTeamBadge).into(holder.image)

        holder.itemView.setOnClickListener {
            context.startActivity<DetailTeam>("data" to item)
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): Filter.FilterResults {

                val charString = charSequence.toString()

                if (charString.isEmpty()) {
                    listFiltered = list
                } else {
                    val filteredList = arrayListOf<TeamModel>()
                    for (row in list) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.strTeam.toLowerCase().contains(charString.toLowerCase())) {

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
                listFiltered = filterResults.values as List<TeamModel>
                notifyDataSetChanged()
            }
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView = itemView.name
        var image: ImageView = itemView.image
    }
}