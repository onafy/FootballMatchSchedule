package onafy.footballmatchschedule.Features.Detail
import onafy.footballmatchschedule.ModelDataClass.Team


interface DetailView {
    fun showLoading()
    fun hideLoading()
    fun showhomeImage(data: List<Team>)
    fun showawayImage(data: List<Team>)
}