package onafy.footballmatchschedule.Features.Main

import com.google.gson.Gson
import onafy.footballmatchschedule.Api.ApiRepository
import onafy.footballmatchschedule.Api.TheSportDBApi
import onafy.footballmatchschedule.ModelDataClass.Event
import onafy.footballmatchschedule.ModelDataClass.EventResponse
import onafy.footballmatchschedule.TestContextProvider
import org.junit.Test

import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class MainPresenterTest {

    @Mock
    private
    lateinit var view:MainView

    @Mock
    private
    lateinit var TheSportDBApi: TheSportDBApi

    @Mock
    private
    lateinit var gson: Gson

    @Mock
    private
    lateinit var apiRepository: ApiRepository
    private lateinit var presenter: MainPresenter

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        presenter = MainPresenter(view, apiRepository,gson, TestContextProvider())
    }


    @Test
    fun getEventList() {
        val events: MutableList<Event> = mutableListOf()
        val response = EventResponse(events)
        val matchschedule = "English Premiere League"

        Mockito.`when`(gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getEvents(matchschedule)),
                EventResponse::class.java
        )).thenReturn(response)

        presenter.getEventList(matchschedule)

        if(events.isNotEmpty()){
            Mockito.verify(view).showLoading()
            Mockito.verify(view).showEventList(events)
            Mockito.verify(view).hideLoading()
        }
    }
}