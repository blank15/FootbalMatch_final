package id.kampung.footballmatch.view.team

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.Menu
import android.view.MenuItem
import com.bumptech.glide.Glide
import id.kampung.footballmatch.R
import id.kampung.footballmatch.adapter.TabTeamAdapter
import id.kampung.footballmatch.data.local.FavoriteTeamDao
import id.kampung.footballmatch.data.local.database
import id.kampung.footballmatch.data.model.LeaguesList
import id.kampung.footballmatch.data.model.PlayerModel
import id.kampung.footballmatch.data.model.TeamModel
import id.kampung.footballmatch.repository.RemoteRepository
import kotlinx.android.synthetic.main.activity_detail_team.*
import org.jetbrains.anko.db.StringParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.toast

class DetailTeam : AppCompatActivity(), TeamContract {


    private lateinit var teamModel: TeamModel
    private lateinit var tabTeamAdapter: TabTeamAdapter
    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false
    private lateinit var presenter: TeamPresenter
    private lateinit var id: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_team)
        teamModel = intent.getParcelableExtra("data")
        id = teamModel.idTeam

        Glide.with(this).load(teamModel.strTeamBadge).into(teamIvLogo)
        teamTvName.text = teamModel.strTeam
        teamTvStadium.text = teamModel.strStadium
        teamTvYear.text = teamModel.intFormedYear

        tabTeamAdapter = TabTeamAdapter(supportFragmentManager, teamModel)
        teamViewPager.adapter = tabTeamAdapter
        teamTabLayout.setupWithViewPager(teamViewPager)

        presenter = TeamPresenter(this, RemoteRepository())

        favoriteState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun favoriteState() {
        database.use {
            val result = select(FavoriteTeamDao.TABLE_FAVORITE)
                    .whereArgs("(TEAM_ID = {id})",
                            "id" to id)
            val favorite = result.column(FavoriteTeamDao.JSON_DATA).parseList(StringParser)
            isFavorite = favorite.isNotEmpty()

        }
        setFavorite()
    }

    override fun onFailed(message: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onSuccess(eventsList: List<TeamModel>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onSuccesPlayer(playerModel: List<PlayerModel>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onLeaguesGet(leaguesList: LeaguesList?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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
                if (isFavorite) presenter.removeFromFavorite(teamModel.idTeam, this) else presenter.addToFavorite(teamModel, this)
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
