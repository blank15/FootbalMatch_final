package id.kampung.footballmatch.view.match

import id.kampung.footballmatch.data.model.EventModel
import id.kampung.footballmatch.data.model.LeaguesList

interface MatchContract {
    fun onFailed(message: String?)
    fun onSuccess(eventsList: List<EventModel>?)
    fun onLeaguesGet(leaguesList: LeaguesList?)
}