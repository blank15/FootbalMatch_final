package id.kampung.footballmatch.view.team

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import com.google.gson.Gson
import id.kampung.footballmatch.data.local.FavoriteDao
import id.kampung.footballmatch.data.local.FavoriteTeamDao
import id.kampung.footballmatch.data.local.database
import id.kampung.footballmatch.data.model.*
import id.kampung.footballmatch.repository.RemoteRepository
import id.kampung.footballmatch.repository.RemoteRepositoryCallback
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert

class TeamPresenter(private val teamContract: TeamContract, private val remoteRepository: RemoteRepository) {

    fun getAllTeamLeaguen(id: String?) {

        remoteRepository.getAllTeam(id, object : RemoteRepositoryCallback<TeamList?> {
            override fun onDataLoaded(data: TeamList?) {
                teamContract.onSuccess(data?.teams)
            }

            override fun onDataError(error: String?) {
                teamContract.onFailed(error)
            }

        })

    }

    fun getAllPlayer(id: String?) {

        remoteRepository.getAllPlayer(id, object : RemoteRepositoryCallback<PlayerList?> {
            override fun onDataLoaded(data: PlayerList?) {
                teamContract.onSuccesPlayer(data?.player)
            }

            override fun onDataError(error: String?) {
                teamContract.onFailed(error)
            }
        })

    }

    fun getLeagues() {
        remoteRepository.getLeagues(object : RemoteRepositoryCallback<LeaguesList?> {
            override fun onDataLoaded(data: LeaguesList?) {
                teamContract.onLeaguesGet(data)
            }

            override fun onDataError(error: String?) {
                teamContract.onFailed(error)
            }

        })
    }

    fun addToFavorite(data: TeamModel?, context: Context) {
        try {
            val gson = Gson()
            val json = gson.toJson(data)
            context.database.use {
                insert(FavoriteTeamDao.TABLE_FAVORITE,
                        FavoriteTeamDao.TEAM_ID to data?.idTeam,
                        FavoriteDao.JSON_DATA to json)
            }
            teamContract.onSuccesFavorite("Added to favorite")
        } catch (e: SQLiteConstraintException) {
            teamContract.onFailed(e.localizedMessage)
        }
    }

    fun removeFromFavorite(id: String, context: Context) {
        try {
            context.database.use {
                delete(FavoriteTeamDao.TABLE_FAVORITE, "(TEAM_ID = {id})", "id" to id)
            }
            teamContract.onSuccesUnfavorite("Remove from favorite")

        } catch (e: SQLiteConstraintException) {
            teamContract.onFailed(e.localizedMessage)
        }
    }

}