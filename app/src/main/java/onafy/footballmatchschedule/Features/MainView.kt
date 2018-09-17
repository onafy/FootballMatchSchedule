package onafy.footballmatchschedule.Features

import onafy.footballmatchschedule.ModelDataClass.Event

interface MainView {
        fun showLoading()
        fun hideLoading()
        fun showEventList(data: List<Event>)

}