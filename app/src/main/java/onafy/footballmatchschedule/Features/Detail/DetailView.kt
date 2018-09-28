package onafy.footballmatchschedule.Features.Detail
import onafy.footballmatchschedule.ModelDataClass.Event
import onafy.footballmatchschedule.ModelDataClass.Team


interface DetailView {
    fun showLoading()
    fun hideLoading()
    fun showHomeImage(data: List<Team>)
    fun showAwayImage(data: List<Team>)
    fun showDetail(data: List<Event>)
}