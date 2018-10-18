package id.kampung.footballmatch.view.detailMatch

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import com.google.gson.Gson
import id.kampung.footballmatch.data.local.FavoriteDao
import id.kampung.footballmatch.data.local.database
import id.kampung.footballmatch.data.model.EventModel
import id.kampung.footballmatch.data.model.TeamList
import id.kampung.footballmatch.repository.RemoteRepository
import id.kampung.footballmatch.repository.RemoteRepositoryCallback
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert

class DetailPresenter(private val detailContract: DetailContract, private val remoteRepository: RemoteRepository) {

    fun getHomeLogo(id: String) {
        remoteRepository.getLogo(id, object : RemoteRepositoryCallback<TeamList?> {
            override fun onDataLoaded(data: TeamList?) {
                detailContract.onSuccessLoadHome(data)

            }

            override fun onDataError(error: String?) {
                detailContract.onFailed(error)
            }

        })

    }

    fun getAwayLogo(id: String) {
        remoteRepository.getLogo(id, object : RemoteRepositoryCallback<TeamList?> {
            override fun onDataLoaded(data: TeamList?) {
                detailContract.onSuccessLoadAway(
                        data)
            }

            override fun onDataError(error: String?) {
                detailContract.onFailed(error)
            }

        })


    }

    fun addToFavorite(data: EventModel?, context: Context) {
        try {
            val gson = Gson()
            val json = gson.toJson(data)
            context.database.use {
                insert(FavoriteDao.TABLE_FAVORITE,
                        FavoriteDao.EVENT_ID to data?.idEvent,
                        FavoriteDao.JSON_DATA to json)
            }
            detailContract.onSuccesFavorite("Added to favorite")
        } catch (e: SQLiteConstraintException) {
            detailContract.onFailed(e.localizedMessage)
        }
    }

    fun removeFromFavorite(id: String, context: Context) {
        try {
            context.database.use {
                delete(FavoriteDao.TABLE_FAVORITE, "(EVENT_ID = {id})", "id" to id)
            }
            detailContract.onSuccesUnfavorite("Remove from favorite")

        } catch (e: SQLiteConstraintException) {
            detailContract.onFailed(e.localizedMessage)
        }
    }
}