package id.kampung.footballmatch.view.favorite

import id.kampung.footballmatch.data.model.EventModel
import id.kampung.footballmatch.data.model.TeamModel

interface FavoriteContract {
    fun onFailed(message: String?)
    fun onSuccess(eventsList: List<EventModel>)
    fun onSuccessTeam(teamList: List<TeamModel>)
}