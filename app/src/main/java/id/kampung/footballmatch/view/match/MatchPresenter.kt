package id.kampung.footballmatch.view.match

import id.kampung.footballmatch.data.model.LeaguesList
import id.kampung.footballmatch.data.model.MatchModel
import id.kampung.footballmatch.repository.RemoteRepository
import id.kampung.footballmatch.repository.RemoteRepositoryCallback

class MatchPresenter(private val matchContract: MatchContract, private val remoteRepository: RemoteRepository) {

    fun getLastMatch() {
        remoteRepository.getLastMatch(object : RemoteRepositoryCallback<MatchModel?> {
            override fun onDataLoaded(data: MatchModel?) {
                matchContract.onSuccess(data?.events)
            }

            override fun onDataError(error: String?) {
                matchContract.onFailed(error)
            }

        })
    }


    fun getLastMatchByID(id: String?) {
        remoteRepository.getLastMatchById(id, object : RemoteRepositoryCallback<MatchModel?> {
            override fun onDataLoaded(data: MatchModel?) {
                matchContract.onSuccess(data?.events)
            }

            override fun onDataError(error: String?) {
                matchContract.onFailed(error)
            }

        })
    }


    fun getNextMatchByID(id: String?) {
        remoteRepository.getNextMatchById(id, object : RemoteRepositoryCallback<MatchModel?> {
            override fun onDataLoaded(data: MatchModel?) {
                matchContract.onSuccess(data?.events)
            }

            override fun onDataError(error: String?) {
                matchContract.onFailed(error)
            }

        })
    }

    fun getNextMatch() {
        remoteRepository.getNextMatch(object : RemoteRepositoryCallback<MatchModel?> {
            override fun onDataLoaded(data: MatchModel?) {
                matchContract.onSuccess(data?.events)
            }

            override fun onDataError(error: String?) {
                matchContract.onFailed(error)
            }

        })
    }

    fun getLeagues() {
        remoteRepository.getLeagues(object : RemoteRepositoryCallback<LeaguesList?> {
            override fun onDataLoaded(data: LeaguesList?) {
                matchContract.onLeaguesGet(data)
            }

            override fun onDataError(error: String?) {
                matchContract.onFailed(error)
            }

        })
    }
}