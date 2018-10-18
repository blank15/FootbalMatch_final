package id.kampung.footballmatch.repository

import id.kampung.footballmatch.data.model.LeaguesList
import id.kampung.footballmatch.data.model.MatchModel
import id.kampung.footballmatch.data.model.PlayerList
import id.kampung.footballmatch.data.model.TeamList
import id.kampung.footballmatch.data.remote.ApiService
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class RemoteRepository {

    fun getLastMatch(callback: RemoteRepositoryCallback<MatchModel?>) {
        ApiService
                .getInstance()
                .matchService()
                .getLastMatches()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { matchList ->
                            callback.onDataLoaded(matchList)

                        },
                        { error ->
                            callback.onDataError(error.message)
                        }
                )
    }

    fun getLastMatchById(id: String?, callback: RemoteRepositoryCallback<MatchModel?>) {
        ApiService
                .getInstance()
                .leaguesService()
                .getLastMatchesByID(id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { matchList ->
                            callback.onDataLoaded(matchList)

                        },
                        { error ->
                            callback.onDataError(error.message)
                        }
                )
    }

    fun getNextMatch(callback: RemoteRepositoryCallback<MatchModel?>) {
        ApiService
                .getInstance()
                .matchService()
                .getNextMatches()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { matchList ->
                            callback.onDataLoaded(matchList)

                        },
                        { error ->
                            callback.onDataError(error.message)
                        }
                )
    }

    fun getNextMatchById(id: String?, callback: RemoteRepositoryCallback<MatchModel?>) {
        ApiService
                .getInstance()
                .leaguesService()
                .getNextMatchesByID(id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { matchList ->
                            callback.onDataLoaded(matchList)

                        },
                        { error ->
                            callback.onDataError(error.message)
                        }
                )
    }

    fun getLeagues(callback: RemoteRepositoryCallback<LeaguesList?>) {
        ApiService
                .getInstance()
                .leaguesService()
                .getAllLeagues()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { leagues ->
                            callback.onDataLoaded(leagues)
                        }, { error ->
                    callback.onDataError(error.message)
                }
                )
    }

    fun getLogo(id: String, callback: RemoteRepositoryCallback<TeamList?>) {
        ApiService
                .getInstance()
                .teamService()
                .getInfoTeam(id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { teams ->
                            callback.onDataLoaded(teams)

                        },
                        { error ->
                            callback.onDataError(error.message)
                        }
                )

    }

    fun getAllTeam(id: String?, callback: RemoteRepositoryCallback<TeamList?>) {
        ApiService
                .getInstance()
                .teamService()
                .getInfoAllTeam(id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { teams ->
                            callback.onDataLoaded(teams)

                        },
                        { error ->
                            callback.onDataError(error.message)
                        }
                )

    }


    fun getAllPlayer(id: String?, callback: RemoteRepositoryCallback<PlayerList?>) {
        ApiService
                .getInstance()
                .teamService()
                .getInfoAllPlayer(id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { players ->
                            callback.onDataLoaded(players)

                        },
                        { error ->
                            callback.onDataError(error.message)
                        }
                )

    }


}