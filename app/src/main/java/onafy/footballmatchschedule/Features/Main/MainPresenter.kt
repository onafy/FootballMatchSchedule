package onafy.footballmatchschedule.Features.Main

import com.google.gson.Gson
import onafy.footballmatchschedule.Api.ApiRepository
import onafy.footballmatchschedule.Api.TheSportDBApi
import onafy.footballmatchschedule.ModelDataClass.EventResponse
import onafy.footballmatchschedule.Util.CoroutineContextProvider
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MainPresenter(private val view: MainView,
                    private val apiRepository: ApiRepository,
                    private val gson: Gson,
                    private val context: CoroutineContextProvider = CoroutineContextProvider()) {


    fun getEventList(matchschedule: String?) {
        view.showLoading()
        doAsync {
            if (matchschedule != "Favorites") {
                val data = gson.fromJson(apiRepository.doRequest(TheSportDBApi.getEvents(matchschedule)),
                        EventResponse::class.java)
                uiThread {
                    view.hideLoading()
                    view.showEventList(data.events)
                }

            } else {
                uiThread {
                    view.hideLoading()
                    view.showFav()
                }
            }
        }
    }
}