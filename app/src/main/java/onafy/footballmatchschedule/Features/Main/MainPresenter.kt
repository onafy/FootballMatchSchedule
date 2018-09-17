package onafy.footballmatchschedule.Features.Main

import android.util.Log
import com.google.gson.Gson
import onafy.footballmatchschedule.Api.ApiRepository
import onafy.footballmatchschedule.Api.TheSportDBApi
import onafy.footballmatchschedule.ModelDataClass.EventResponse
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MainPresenter(private val view: MainView,
                    private val apiRepository: ApiRepository,
                    private val gson: Gson) {


    fun getEventList(matchschedule: String?) {
        view.showLoading()
        doAsync {
            val data = gson.fromJson(apiRepository.doRequest(TheSportDBApi.getEvents(matchschedule)),
                    EventResponse::class.java)
            Log.d(data.toString(), "data log presener")

            uiThread {
                view.hideLoading()
                view.showEventList(data.events)
            }
        }
    }
}