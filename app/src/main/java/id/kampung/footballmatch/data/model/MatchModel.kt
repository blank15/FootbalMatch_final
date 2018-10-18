package id.kampung.footballmatch.data.model

import com.google.gson.annotations.SerializedName

class MatchModel(@SerializedName("events")
                 val events: List<EventModel>?)