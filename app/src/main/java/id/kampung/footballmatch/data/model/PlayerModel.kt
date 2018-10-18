package id.kampung.footballmatch.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class PlayerModel(@SerializedName("strPlayer")
                  val strPlayer: String,
                  @SerializedName("strDescriptionEN")
                  val strDescriptionEN: String,
                  @SerializedName("dateBorn")
                  val dateBorn: String,
                  @SerializedName("strThumb")
                  val strThumb: String,
                  @SerializedName("strWeight")
                  val strWeight: String,
                  @SerializedName("strPosition")
                  val strPosition: String,
                  @SerializedName("strHeight")
                  val strHeight: String) : Parcelable