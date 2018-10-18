package id.kampung.footballmatch.data.remote

import id.kampung.footballmatch.BuildConfig
import id.kampung.footballmatch.data.model.LeaguesList
import id.kampung.footballmatch.data.model.LeaguesModel
import id.kampung.footballmatch.data.model.MatchModel
import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable

interface LeaguesService {
    @GET("api/v1/json/"
            + BuildConfig.TSDB_API_KEY
            + "/all_leagues.php")
    fun getAllLeagues(): Observable<LeaguesList>

    @GET("api/v1/json/"
            + BuildConfig.TSDB_API_KEY
            + "/eventspastleague.php")
    fun getLastMatchesByID(@Query("id") leagueId: String?): Observable<MatchModel>

    @GET("api/v1/json/"
            + BuildConfig.TSDB_API_KEY
            + "/eventsnextleague.php?id=4328")
    fun getNextMatchesByID(@Query("id") leagueId: String?): Observable<MatchModel>
}