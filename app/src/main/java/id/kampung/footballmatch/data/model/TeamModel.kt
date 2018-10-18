package id.kampung.footballmatch.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class TeamModel(
        @SerializedName("idTeam")
        val idTeam: String,
        @SerializedName("strTeamBadge")
        val strTeamBadge: String?,
        @SerializedName("strDescriptionEN")
        val strDescriptionEN: String,
        @SerializedName("strStadium")
        val strStadium: String,
        @SerializedName("intFormedYear")
        val intFormedYear: String,
        @SerializedName("strTeam")
        val strTeam: String) : Parcelable