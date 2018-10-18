package id.kampung.footballmatch.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.text.SimpleDateFormat
import java.util.*

@Parcelize
class EventModel(@SerializedName("idEvent")
                 val idEvent: String,
                 @SerializedName("strHomeTeam")
                 val strHomeTeam: String,
                 @SerializedName("strAwayTeam")
                 val strAwayTeam: String,
                 @SerializedName("intHomeScore")
                 val intHomeScore: String?,
                 @SerializedName("intAwayScore")
                 val intAwayScore: String?,
                 @SerializedName("strHomeGoalDetails")
                 val strHomeGoalDetails: String?,
                 @SerializedName("strHomeLineupGoalkeeper")
                 val strHomeLineupGoalkeeper: String?,
                 @SerializedName("strHomeLineupDefense")
                 val strHomeLineupDefense: String?,
                 @SerializedName("strHomeLineupMidfield")
                 val strHomeLineupMidfield: String?,
                 @SerializedName("strHomeLineupForward")
                 val strHomeLineupForward: String?,
                 @SerializedName("strHomeLineupSubstitutes")
                 val strHomeLineupSubstitutes: String?,
                 @SerializedName("strAwayGoalDetails")
                 val strAwayGoalDetails: String?,
                 @SerializedName("strAwayLineupGoalkeeper")
                 val strAwayLineupGoalkeeper: String?,
                 @SerializedName("strAwayLineupDefense")
                 val strAwayLineupDefense: String?,
                 @SerializedName("strAwayLineupMidfield")
                 val strAwayLineupMidfield: String?,
                 @SerializedName("strAwayLineupForward")
                 val strAwayLineupForward: String?,
                 @SerializedName("strAwayLineupSubstitutes")
                 val strAwayLineupSubstitutes: String?,
                 private val strDate: String?,
                 @SerializedName("strTime")
                 val strTime: String?,
                 @SerializedName("idHomeTeam")
                 val idHomeTeam: String = "",
                 @SerializedName("idAwayTeam")
                 val idAwayTeam: String = "") : Parcelable {

    fun toFormattedDate(): String {
        val dateEvent = SimpleDateFormat("dd/MM/yy HH:mm:ssXXX", Locale.ENGLISH).parse("$strDate $strTime")
        return SimpleDateFormat("EEEE, dd MMM yyyy - HH:mm", Locale.getDefault()).format(dateEvent)
    }

    fun toDate(): Date? {
        if (strDate != null) {
            return SimpleDateFormat("dd/MM/yy HH:mm:ssXXX", Locale.ENGLISH).parse("$strDate $strTime")
        }
        return null
    }
}