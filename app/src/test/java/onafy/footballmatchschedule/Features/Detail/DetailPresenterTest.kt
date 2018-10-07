package onafy.footballmatchschedule.Features.Detail

import com.google.gson.Gson
import onafy.footballmatchschedule.Api.ApiRepository
import onafy.footballmatchschedule.Api.TheSportDBApi
import onafy.footballmatchschedule.ModelDataClass.Event
import onafy.footballmatchschedule.ModelDataClass.EventResponse
import onafy.footballmatchschedule.ModelDataClass.Team
import onafy.footballmatchschedule.ModelDataClass.TeamResponse
import onafy.footballmatchschedule.TestContextProvider
import org.junit.Test

import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class DetailPresenterTest {
    @Mock
    private
    lateinit var view: DetailView

    @Mock
    private
    lateinit var TheSportDBApi: TheSportDBApi

    @Mock
    private
    lateinit var gson: Gson

    @Mock
    private
    lateinit var apiRepository: ApiRepository
    private lateinit var presenter: DetailPresenter

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        presenter = DetailPresenter(view, apiRepository,gson, TestContextProvider())
    }

    @Test
    fun getTeamDetail() {
        val events: MutableList<Event> = mutableListOf()
        val teams: MutableList<Team> = mutableListOf()
        val response = EventResponse(events)
        val response2 = TeamResponse(teams)
        val eventId = "579152"
        val homeId = "133935"
        val awayId = "133657"

        Mockito.`when`(gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getEventDetail(eventId)),
                EventResponse::class.java
        )).thenReturn(response)

        Mockito.`when`(gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getTeamsById(homeId)),
                TeamResponse::class.java
        )).thenReturn(response2)

        Mockito.`when`(gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getTeamsById(awayId)),
                TeamResponse::class.java
        )).thenReturn(response2)


        presenter.getTeamDetail(eventId, homeId, awayId)

        if(events.isNotEmpty()){
            Mockito.verify(view).showLoading()
            Mockito.verify(view).showAwayImage(teams)
            Mockito.verify(view).showHomeImage(teams)
            Mockito.verify(view).showDetail(events)
            Mockito.verify(view).hideLoading()
        }
    }
}