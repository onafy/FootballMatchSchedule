package onafy.footballmatchschedule.Features.Detail

import android.database.sqlite.SQLiteConstraintException
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.view.Menu
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.google.gson.Gson
import kotlinx.android.synthetic.main.detail_pastmatch.*
import onafy.footballmatchschedule.Api.ApiRepository
import onafy.footballmatchschedule.DBLokal.Favorite
import onafy.footballmatchschedule.DBLokal.database
import onafy.footballmatchschedule.ModelDataClass.Event
import onafy.footballmatchschedule.ModelDataClass.Team
import onafy.footballmatchschedule.R.drawable.ic_add_to_favorites
import onafy.footballmatchschedule.R.drawable.ic_added_to_favorites
import onafy.footballmatchschedule.R.id.add_to_favorite
import onafy.footballmatchschedule.R.menu.detail_menu
import onafy.footballmatchschedule.R
import onafy.footballmatchschedule.R.color.colorAccent
import onafy.footballmatchschedule.Util.invisible
import onafy.footballmatchschedule.Util.visible
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class DetailActivity : AppCompatActivity(), DetailView {
    //=================================== declaration ===========================================
    private lateinit var homeTeamObj: Team
    private lateinit var awayTeamObj: Team
    private lateinit var presenter: DetailPresenter
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var id: String

    private var eventId: String = ""
    private var homeId: String = ""
    private var awayId : String = ""
    private var homeBadge: String = ""
    private var awayBadge: String = ""
    private var eventDate: String = ""


    private lateinit var eventdetail: Event
    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false
    //=============================================================================================

    //============================= main =========================================================
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_pastmatch)

        declaration()
        showActionBar()
        favoriteState()
        val request = ApiRepository()
        val gson = Gson()
        presenter = DetailPresenter(this, request, gson)
        presenter.getTeamDetail(eventId, homeId, awayId)
        swipeRefresh.onRefresh {
            presenter.getTeamDetail(eventId, homeId, awayId)
        }

    } //==========================================================================================



    // ============================= function ====================================================

    private fun declaration(){
        eventId = intent.getStringExtra("eventId")
        homeId = intent.getStringExtra("homeId")
        awayId = intent.getStringExtra("awayId")

        swipeRefresh = findViewById<SwipeRefreshLayout>(R.id.swipeRefresh)
        swipeRefresh.setColorSchemeResources(colorAccent,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light)
    }

    private fun showActionBar(){
        val actionbar = supportActionBar
        actionbar!!.title = "Detail"
        actionbar.setDisplayHomeAsUpEnabled(true)
    }

    override fun showhomeImage(data: List<Team>) {
        swipeRefresh.isRefreshing = false
        homeTeamObj = data[0]
        homeBadge = homeTeamObj.teamBadge
        Glide.with(this).load(homeBadge).into(homeImage)

    }

    override fun showawayImage(data: List<Team>) {
        swipeRefresh.isRefreshing = false
        awayTeamObj = data[0]
        awayBadge = awayTeamObj.teamBadge
        Glide.with(this).load(awayBadge).into(awayImage)
    }

    override fun showDetail(data: List<Event>) {
        swipeRefresh.isRefreshing = false
        eventdetail = Event(data[0].eventId,
                data[0].homeName,
                data[0].awayName,
                data[0].eventDate,
                data[0].homeScore,
                data[0].awayScore,
                data[0].homeId,
                data[0].awayId)

        dateventTV.text = data[0].eventDate
        homenameTV.text = data[0].homeName
        awaynameTV.text = data[0].awayName

        if(data[0].homeGoalDetails !=null)
        {
            homescoreTV.text = data[0].homeScore
            awayscoreTV.text = data[0].awayScore
            homegoalsTV.text = data[0].homeGoalDetails
            awaygoalsTV.text = data[0].awayGoalDetails
            awaygoalkeeperTV.text = data[0].awayGoalKeeper
            homegoalkeeperTV.text = data[0].homeGoalKeeper
            homeshotsTV.text = data[0].homeShots
            awayshotsTV.text = data[0].awayShots
            homedefenseTV.text = data[0].homeDefense
            awaydefenseTV.text = data[0].awayDefense
            homemidfieldTV.text = data[0].homeMidfield
            awaymidfieldTV.text = data[0].awayMidfield
            homeforwardTV.text = data[0].homeForward
            awayforwardTV.text = data[0].awayForward
            homesubtitutesTV.text = data[0].homeSubtitutes
            awaysubtitutesTV.text = data[0].awaySubtitutes
        }
    }


    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun onSupportNavigateUp() : Boolean{
        onBackPressed()
        return true
    }
    // ===========================================================================================

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(detail_menu, menu)
        menuItem = menu
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            add_to_favorite -> {
                if (isFavorite) removeFromFavorite() else addToFavorite()
                isFavorite = !isFavorite
                setFavorite()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun addToFavorite(){
        try{
            database.use{
                insert(Favorite.TABLE_FAVORITE,
                        Favorite.EVENT_ID to eventdetail.eventId,
                        Favorite.HOME_NAME to eventdetail.homeName,
                        Favorite.AWAY_NAME to eventdetail.awayName,
                        Favorite.EVENT_DATE to eventdetail.eventDate,
                        Favorite.HOME_SCORE to eventdetail.homeScore,
                        Favorite.AWAY_SCORE to eventdetail.awayScore,
                        Favorite.HOME_ID to eventdetail.homeId,
                        Favorite.AWAY_ID to eventdetail.awayId)
            }
            snackbar(swipeRefresh, "Added to Favorite").show()
        } catch (e: SQLiteConstraintException){
            snackbar(swipeRefresh, e.localizedMessage).show()
        }
    }


    private fun removeFromFavorite(){
        try {
            database.use {
                delete(Favorite.TABLE_FAVORITE, "(EVENT_ID = {eventId})",
                        "eventId" to eventId)
            }
            snackbar(swipeRefresh, "Removed to favorite").show()
        } catch (e: SQLiteConstraintException){
            snackbar(swipeRefresh, e.localizedMessage).show()
        }
    }

    private fun setFavorite() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_added_to_favorites)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_add_to_favorites)
    }

    private fun favoriteState(){
        database.use {
            val result = select(Favorite.TABLE_FAVORITE)
                    .whereArgs("(EVENT_ID = {eventId})",
                            "eventId" to eventId)
            val favorite = result.parseList(classParser<Favorite>())
            if (!favorite.isEmpty()) isFavorite = true
        }
    }


}

