package id.kampung.footballmatch.view.favorite

import android.content.Context
import com.google.gson.Gson
import id.kampung.footballmatch.data.local.FavoriteDao
import id.kampung.footballmatch.data.local.FavoriteTeamDao
import id.kampung.footballmatch.data.local.database
import id.kampung.footballmatch.data.model.EventModel
import id.kampung.footballmatch.data.model.TeamModel
import org.jetbrains.anko.db.MapRowParser
import org.jetbrains.anko.db.parseList
import org.jetbrains.anko.db.select

class FavoritePresenter(private val favoriteContract: FavoriteContract) {

    fun getFavoriteData(context: Context) {
        context.database.use {
            select(FavoriteDao.TABLE_FAVORITE).exec {
                val data = this.parseList(MyRowParser())
                favoriteContract.onSuccess(data)
            }
        }
    }

    fun getFavoriteTeamData(context: Context) {
        context.database.use {
            select(FavoriteTeamDao.TABLE_FAVORITE).exec {
                val data = this.parseList(MyRowParserTeam())
                favoriteContract.onSuccessTeam(data)
            }
        }
    }

    class MyRowParser : MapRowParser<EventModel> {
        override fun parseRow(columns: Map<String, Any?>): EventModel {
            val json: String? = columns[FavoriteDao.JSON_DATA].toString()
            return Gson().fromJson<EventModel>(json, EventModel::class.java)
        }

    }

    class MyRowParserTeam : MapRowParser<TeamModel> {
        override fun parseRow(columns: Map<String, Any?>): TeamModel {
            val json: String? = columns[FavoriteTeamDao.JSON_DATA].toString()
            return Gson().fromJson<TeamModel>(json, TeamModel::class.java)
        }

    }

}

