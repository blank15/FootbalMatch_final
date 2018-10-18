package id.kampung.footballmatch.view.team

import id.kampung.footballmatch.data.model.LeaguesList
import id.kampung.footballmatch.data.model.PlayerModel
import id.kampung.footballmatch.data.model.TeamModel

interface TeamContract {
    fun onFailed(message: String?)
    fun onSuccess(eventsList: List<TeamModel>?)
    fun onSuccesPlayer(playerModel: List<PlayerModel>?)
    fun onLeaguesGet(leaguesList: LeaguesList?)
    fun onSuccesFavorite(message: String)
    fun onSuccesUnfavorite(message: String)
}