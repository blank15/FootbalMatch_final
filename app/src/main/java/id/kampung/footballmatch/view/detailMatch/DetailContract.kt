package id.kampung.footballmatch.view.detailMatch

import id.kampung.footballmatch.data.model.TeamList


interface DetailContract {
    fun onFailed(message: String?)
    fun onSuccessLoadHome(teamLogo: TeamList?)
    fun onSuccessLoadAway(teamLogo: TeamList?)
    fun onSuccesFavorite(message: String)
    fun onSuccesUnfavorite(message: String)
}