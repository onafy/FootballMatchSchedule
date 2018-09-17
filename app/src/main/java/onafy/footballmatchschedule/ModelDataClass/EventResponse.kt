package onafy.footballmatchschedule.ModelDataClass

import com.google.gson.annotations.SerializedName

class EventResponse(
        @SerializedName("events") var events: List<Event>)