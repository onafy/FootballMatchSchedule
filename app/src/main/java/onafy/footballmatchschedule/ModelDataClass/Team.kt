package onafy.footballmatchschedule.ModelDataClass

import com.google.gson.annotations.SerializedName

data class Team(
    @SerializedName("strTeamBadge")
    var teamBadge: String = ""

)
