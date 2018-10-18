package id.kampung.footballmatch.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class LeaguesModel(@SerializedName("idLeague")
                   val idLeague: String?,
                   @SerializedName("strLeague")
                   val strLeague: String) : Parcelable