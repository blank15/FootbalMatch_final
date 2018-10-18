package id.kampung.footballmatch.data.remote

import com.google.gson.GsonBuilder
import id.kampung.footballmatch.BuildConfig
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object ApiService {
    private val gsonBuilder = GsonBuilder().create()

    private val retrofitBuilder: Retrofit = Retrofit.Builder()
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gsonBuilder))
            .baseUrl(BuildConfig.BASE_URL)
            .build()

    private object ApiServiceHolder {
        internal val INSTANCE = ApiService
    }

    fun getInstance(): ApiService {
        return ApiServiceHolder.INSTANCE
    }

    private fun <E> createService(serviceClass: Class<E>): E {

        return retrofitBuilder.create(serviceClass)
    }

    fun matchService(): MatchService {
        return createService(MatchService::class.java)
    }

    fun teamService(): TeamService {
        return createService(TeamService::class.java)
    }

    fun leaguesService(): LeaguesService {
        return createService(LeaguesService::class.java)
    }

}