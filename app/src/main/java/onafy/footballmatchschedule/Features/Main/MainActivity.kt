package onafy.footballmatchschedule.Features.Main

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.*
import com.google.gson.Gson
import onafy.footballmatchschedule.Api.ApiRepository
import onafy.footballmatchschedule.Features.Detail.DetailActivity
import onafy.footballmatchschedule.ModelDataClass.Event
import onafy.footballmatchschedule.R

import onafy.footballmatchschedule.R.array.eventtype
import onafy.footballmatchschedule.Util.invisible
import onafy.footballmatchschedule.Util.visible
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout
import android.util.Log
import onafy.footballmatchschedule.DBLokal.Favorite
import onafy.footballmatchschedule.DBLokal.database
import onafy.footballmatchschedule.Features.Favorites.FavoriteAdapter
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select


class MainActivity : AppCompatActivity(), MainView {
    private var favorites: MutableList<Favorite> = mutableListOf()
    private var events: MutableList<Event> = mutableListOf()
    private lateinit var listEvent: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var spinner: Spinner
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var presenter: MainPresenter
    private lateinit var adapter: MainAdapter
    private lateinit var adapter2: FavoriteAdapter
    private lateinit var eventType : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ========================= UI ===========================================================
        linearLayout {
            lparams(width= matchParent, height = wrapContent)
            orientation = LinearLayout.VERTICAL
            topPadding = dip(16)
            leftPadding = dip(16)
            rightPadding = dip(16)

            spinner = spinner()
            swipeRefresh = swipeRefreshLayout{
                setColorSchemeResources(R.color.colorAccent,
                        android.R.color.holo_green_light,
                        android.R.color.holo_orange_light,
                        android.R.color.holo_red_light)
                relativeLayout{
                    lparams(width= matchParent, height= wrapContent)

                    listEvent = recyclerView{
                        lparams(width= matchParent, height = wrapContent)
                        layoutManager = LinearLayoutManager(ctx)
                    }
                    progressBar =  progressBar{}
                            .lparams{
                                centerHorizontally()
                            }
                }
            }
        }//========================================================================================

        //================================== recyclerView ========================================
        // this adapter is used for nextEvent and pastEvent
        adapter = MainAdapter(ctx, events) {
            startActivity(intentFor<DetailActivity>(
                    "eventId" to it.eventId,
                    "homeId" to it.homeId,
                    "awayId" to it.awayId))
        }

        //this adapter is used for favorite
        adapter2 = FavoriteAdapter(ctx, favorites) {
            startActivity(intentFor<DetailActivity>(
                    "eventId" to it.eventId,
                    "homeId" to it.homeId,
                    "awayId" to it.awayId))
        }

        listEvent.adapter = adapter
        val request = ApiRepository()
        val gson = Gson()
        presenter = MainPresenter(this, request, gson)
        // =====================================================================================


        // ================================= Spinner and Swipe  ===========================================
        val spinnerItems = resources.getStringArray(eventtype)
        val spinnerAdapter = ArrayAdapter(ctx, android.R.layout.simple_spinner_dropdown_item, spinnerItems)
        spinner.adapter = spinnerAdapter
        spinner.onItemSelectedListener= object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                events.clear()
                eventType = spinner.selectedItem.toString()
                favOrNot()
                presenter.getEventList(eventType)
            }
        }

        swipeRefresh.onRefresh {
            favOrNot()
            presenter.getEventList(eventType)
        }
        //======================================================================================
    }


    // ===================================== Function ==============================
    override fun showLoading() {
            progressBar.visible()
    }

    override fun hideLoading() {
            progressBar.invisible()
    }

    override fun showEventList(data: List<Event>) {
            swipeRefresh.isRefreshing = false
            events.clear()
            events.addAll(data)
            adapter.notifyDataSetChanged()
    }

    override fun showFav() {
            favorites.clear()
            database.use {
                swipeRefresh.isRefreshing = false
                val result = select(Favorite.TABLE_FAVORITE)
                val favorite = result.parseList(classParser<Favorite>())
                favorites.addAll(favorite)
                Log.d("favorite", favorites.toString())
                Log.d("favorite2", favorite.toString())
                adapter2.notifyDataSetChanged()
                progressBar.invisible()
            }
    }

    private fun favOrNot(){
            if(eventType == "Favorites")
            { listEvent.adapter = adapter2 }
            else
            { listEvent.adapter = adapter }
    }
    // ==========================================================================================


}
