package onafy.footballmatchschedule.Api

import onafy.footballmatchschedule.BuildConfig

object TheSportDBApi {

    fun getEvents(matchschedule: String?): String {
        var matchtype: String = ""

        if(matchschedule == "Next Event")
        {
            matchtype = "eventsnextleague.php"
        }
        else if(matchschedule == "Past Event")
        {
            matchtype = "eventspastleague.php"
        }
        return BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/" + matchtype + "?id=4328"
    }

    fun getTeamsbyId(teamId: String?): String{
        return BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/lookupteam.php?id=" + teamId
    }
}