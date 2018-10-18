package id.kampung.footballmatch.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import id.kampung.footballmatch.R
import id.kampung.footballmatch.data.model.PlayerModel
import id.kampung.footballmatch.view.team.tabDetail.DetailPlayer
import kotlinx.android.synthetic.main.list_player.view.*
import org.jetbrains.anko.startActivity

class AdapterPlayer(val context: Context, private val list: List<PlayerModel>) : RecyclerView.Adapter<AdapterPlayer.ViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_player, p0, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        val playerModel = list[p1]

        p0.name.text = playerModel.strPlayer
        p0.position.text = playerModel.strPosition
        Glide.with(context).load(playerModel.strThumb).into(p0.image)
        p0.itemView.setOnClickListener {
            context.startActivity<DetailPlayer>("data" to playerModel)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView = itemView.tvName
        var image: ImageView = itemView.ivLogo
        var position: TextView = itemView.tvPosition
    }
}