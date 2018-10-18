package id.kampung.footballmatch.data.model

import com.google.gson.annotations.SerializedName

class TeamList(@SerializedName("teams")
               val teams: List<TeamModel>)