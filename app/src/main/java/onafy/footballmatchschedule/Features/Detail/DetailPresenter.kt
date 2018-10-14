package onafy.footballmatchschedule.Features.Detail

import com.google.gson.Gson
import onafy.footballmatchschedule.Api.ApiRepository
import onafy.footballmatchschedule.Api.TheSportDBApi
import onafy.footballmatchschedule.ModelDataClass.EventResponse
import onafy.footballmatchschedule.ModelDataClass.TeamResponse
import onafy.footballmatchschedule.Util.CoroutineContextProvider
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class DetailPresenter(private val view: DetailView,
                      private val apiRepository: ApiRepository,
                      private val gson: Gson,
                      private val context: CoroutineContextProvider = CoroutineContextProvider()) {
    fun getTeamDetail(eventId: String, homeId: String, awayId: String) {
        view.showLoading()
        doAsync {
            //detail
            val detail = gson.fromJson(apiRepository.doRequest(TheSportDBApi.getEventDetail(eventId)),
                    EventResponse::class.java)

            //home
            val data = gson.fromJson(apiRepository.doRequest(TheSportDBApi.getTeamsById(homeId)),
                    TeamResponse::class.java)

            //away
            val data2 = gson.fromJson(apiRepository.doRequest(TheSportDBApi.getTeamsById(awayId)),
                    TeamResponse::class.java)


            uiThread {
                view.hideLoading()
                view.showHomeImage(data.teams)
                view.showAwayImage(data2.teams)
                view.showDetail(detail.events)

            }
        }
    }

}