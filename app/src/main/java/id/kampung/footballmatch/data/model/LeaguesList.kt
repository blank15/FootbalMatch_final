package id.kampung.footballmatch.data.model

import com.google.gson.annotations.SerializedName

class LeaguesList(@SerializedName("leagues")
                  val leagues: List<LeaguesModel>)