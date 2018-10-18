package id.kampung.footballmatch.view.detailMatch


import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.bumptech.glide.Glide
import id.kampung.footballmatch.R
import id.kampung.footballmatch.data.local.FavoriteDao
import id.kampung.footballmatch.data.local.database
import id.kampung.footballmatch.data.model.EventModel
import id.kampung.footballmatch.data.model.TeamList
import kotlinx.android.synthetic.main.detail_layout.*
import org.jetbrains.anko.db.StringParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.toast
import id.kampung.footballmatch.repository.RemoteRepository

class DetailMatch : AppCompatActivity(), DetailContract {


    private lateinit var presenter: DetailPresenter
    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false
    private lateinit var data: EventModel
    private lateinit var id: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_layout)


        supportActionBar?.title = getString(R.string.detail_match)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        presenter = DetailPresenter(this, RemoteRepository())
        setData()

    }

    private fun setData() {

        data = intent.getParcelableExtra("data")
        id = data.idEvent

        presenter.getHomeLogo(data.idHomeTeam)
        presenter.getAwayLogo(data.idAwayTeam)


        favoriteState()

        text_date.text = data.toFormattedDate()
        if (data.intHomeScore.isNullOrEmpty()) {
            score_home.text = "?"
        } else
            score_home.text = data.intHomeScore

        if (data.intAwayScore.isNullOrEmpty()) {
            score_away.text = "?"
        } else
            score_away.text = data.intAwayScore

        text_name_home.text = data.strHomeTeam
        text_name_away.text = data.strAwayTeam

        goalskeeper_home.text = data.strAwayLineupGoalkeeper
        goalskeeper_away.text = data.strHomeLineupGoalkeeper

        goals_name_home.text = data.strHomeGoalDetails?.splitString()
        goals_name_away.text = data.strAwayGoalDetails?.splitString()

        defense_home.text = data.strHomeLineupDefense?.splitString()
        defense_away.text = data.strAwayLineupDefense?.splitString()

        midfield_home.text = data.strHomeLineupMidfield?.splitString()
        midfield_away.text = data.strAwayLineupMidfield?.splitString()

        forward_home.text = data.strHomeLineupForward?.splitString()
        forward_away.text = data.strAwayLineupForward?.splitString()

        subtitutes_home.text = data.strHomeLineupSubstitutes?.splitString()
        subtitutes_away.text = data.strAwayLineupSubstitutes?.splitString()


    }

    private fun favoriteState() {
        database.use {
            val result = select(FavoriteDao.TABLE_FAVORITE)
                    .whereArgs("(EVENT_ID = {id})",
                            "id" to id)
            val favorite = result.column(FavoriteDao.JSON_DATA).parseList(StringParser)
            isFavorite = favorite.isNotEmpty()

        }
        setFavorite()
    }


    private fun String.splitString(): String {
        val parts = split("(?<=;)")
        var result = ""

        for (index in parts.indices) {
            var temp = parts[index].replace("; ", "\n")
            temp = temp.replace(";", "\n")
            result += temp.trim()
        }

        return result
    }

    override fun onFailed(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun onSuccessLoadHome(teamLogo: TeamList?) {
        Glide.with(this)
                .load(teamLogo!!.teams[0].strTeamBadge?.trim())
                .into(logo_home)
    }

    override fun onSuccessLoadAway(teamLogo: TeamList?) {
        Glide.with(this)
                .load(teamLogo!!.teams[0].strTeamBadge?.trim())
                .into(logo_away)
    }

    override fun onSuccesFavorite(message: String) {
        toast(message)
        isFavorite = true
        setFavorite()

    }

    override fun onSuccesUnfavorite(message: String) {
        toast(message)
        isFavorite = false
        setFavorite()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        menuItem = menu
        setFavorite()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            R.id.add_to_favorite -> {
                if (isFavorite) presenter.removeFromFavorite(data.idEvent, this) else presenter.addToFavorite(data, this)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setFavorite() {

        if (isFavorite) {
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorite_white_24dp)
        } else {
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorite_border_white_24dp)
        }

    }


}


