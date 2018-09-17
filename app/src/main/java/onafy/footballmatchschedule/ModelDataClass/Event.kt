package onafy.footballmatchschedule.ModelDataClass

import com.google.gson.annotations.SerializedName

data class Event(
    @SerializedName("idEvent")
    var eventId: String? = null,

    @SerializedName("strHomeTeam")
    var homeName: String? = null,

    @SerializedName("strAwayTeam")
    var awayName: String? = null,

    @SerializedName("strDate")
    var eventDate: String? = null,

    @SerializedName("intHomeScore")
    var homeScore: String? = null,

    @SerializedName("intAwayScore")
    var awayScore: String? = null,


    //============================================ Detail Component =================

    @SerializedName("idHomeTeam")
    var homeId: String? = null,

    @SerializedName("idAwayTeam")
    var awayId: String? = null,

    @SerializedName("strHomeGoalDetails")
    val homeGoalDetails: String? = null,

    @SerializedName("strAwayGoalDetails")
    val awayGoalDetails: String? = null,

    @SerializedName("intHomeShots")
    val homeShots: Int? = null,

    @SerializedName("intAwayShots")
    val awayShots: Int? = null,

    @SerializedName("strHomeLineupGoalkeeper")
    val homeGoalKeeper: String? = null,

    @SerializedName("strAwayLineupGoalkeeper")
    val awayGoalKeeper: String? = null,

    @SerializedName("strHomeLineupDefense")
    val homeDefense: String? = null,

    @SerializedName("strAwayLineupDefense")
    val awayDefense: String? = null,

    @SerializedName("strHomeLineupMidfield")
    val homeMidfield: String? = null,

    @SerializedName("strAwayLineupMidfield")
    val awayMidfield: String? = null,

    @SerializedName("strHomeLineupForward")
    val homeForward: String? = null,

    @SerializedName("strAwayLineupForward")
    val awayForward: String? = null,

    @SerializedName("strHomeLineupSubstitutes")
    val homeSubtitutes: String? = null,

    @SerializedName("strAwayLineupSubstitutes")
    val awaySubtitutes: String? = null


)