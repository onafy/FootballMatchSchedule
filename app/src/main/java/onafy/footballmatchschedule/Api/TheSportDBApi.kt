package onafy.footballmatchschedule.Api

import onafy.footballmatchschedule.BuildConfig

object TheSportDBApi {

    fun getEvents(matchSchedule: String?): String {
        var matchType = ""

        if(matchSchedule == "Next Event")
        {
            matchType = "eventsnextleague.php"
        }
        else if(matchSchedule == "Past Event")
        {
            matchType = "eventspastleague.php"
        }
        return BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/" + matchType + "?id=4328"
    }

    fun getTeamsById(teamId: String?): String{
        return BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/lookupteam.php?id=" + teamId
    }

    fun getEventDetail(eventId: String?): String {
        return BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/lookupevent.php?id=" + eventId
    }
}