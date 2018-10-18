package id.kampung.footballmatch.data.remote

import id.kampung.footballmatch.BuildConfig
import id.kampung.footballmatch.data.model.MatchModel
import retrofit2.http.GET
import rx.Observable

interface MatchService {

    @GET("api/v1/json/"
            + BuildConfig.TSDB_API_KEY
            + "/eventspastleague.php?id=4328")
    fun getLastMatches(): Observable<MatchModel>

    @GET("api/v1/json/"
            + BuildConfig.TSDB_API_KEY
            + "/eventsnextleague.php?id=4328")
    fun getNextMatches(): Observable<MatchModel>
}