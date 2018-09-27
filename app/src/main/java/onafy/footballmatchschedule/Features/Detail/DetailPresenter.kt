package onafy.footballmatchschedule.Features.Detail

import com.google.gson.Gson
import onafy.footballmatchschedule.Api.ApiRepository
import onafy.footballmatchschedule.Api.TheSportDBApi
import onafy.footballmatchschedule.ModelDataClass.EventResponse
import onafy.footballmatchschedule.ModelDataClass.TeamResponse
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class DetailPresenter(private val view: DetailView,
                      private val apiRepository: ApiRepository,
                      private val gson: Gson) {
    fun getTeamDetail(eventId: String, homeId: String, awayId: String) {
        view.showLoading()
        doAsync {
            val detail = gson.fromJson(apiRepository.doRequest(TheSportDBApi.getEventDetail(eventId)),
                    EventResponse::class.java)
            //home
            val data = gson.fromJson(apiRepository.doRequest(TheSportDBApi.getTeamsbyId(homeId)),
                    TeamResponse::class.java)
            //away
            val data2 = gson.fromJson(apiRepository.doRequest(TheSportDBApi.getTeamsbyId(awayId)),
                    TeamResponse::class.java)


            uiThread {
                view.hideLoading()
                view.showhomeImage(data.teams)
                view.showawayImage(data2.teams)
                view.showDetail(detail.events)

            }
        }
    }
    
}