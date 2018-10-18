package id.kampung.footballmatch.data.remote

import id.kampung.footballmatch.BuildConfig
import id.kampung.footballmatch.data.model.PlayerList
import id.kampung.footballmatch.data.model.TeamList
import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable

interface TeamService {

    @GET("api/v1/json/"
            + BuildConfig.TSDB_API_KEY
            + "/lookupteam.php")
    fun getInfoTeam(@Query("id") id: String?): Observable<TeamList>

    @GET("api/v1/json/"
            + BuildConfig.TSDB_API_KEY
            + "/lookup_all_teams.php")
    fun getInfoAllTeam(@Query("id") id: String?): Observable<TeamList>

    @GET("api/v1/json/"
            + BuildConfig.TSDB_API_KEY
            + "/lookup_all_players.php")
    fun getInfoAllPlayer(@Query("id") id: String?): Observable<PlayerList>
}