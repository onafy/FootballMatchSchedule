package onafy.footballmatchschedule.Features.Detail

import android.database.sqlite.SQLiteConstraintException
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
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
import onafy.footballmatchschedule.Util.invisible
import onafy.footballmatchschedule.Util.visible
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class DetailActivity : AppCompatActivity(), DetailView {
    //=================================== declaration ===========================================
    private var teams: MutableList<Team> = mutableListOf()
    private var events: MutableList<Event> = mutableListOf()
    private lateinit var homeTeamObj: Team
    private lateinit var awayTeamObj: Team
    private lateinit var presenter: DetailPresenter
    private var eventId: String = ""
    private var homeName: String = ""
    private var awayName : String = ""
    private var homeId: String = ""
    private var awayId : String = ""
    private var homeScore: String = ""
    private var awayScore : String = ""
    private var homeGoals : String = ""
    private var awayGoals : String = ""
    private var homeShots: String = ""
    private var awayShots : String = ""
    private var homeGoalKeeper : String = ""
    private var awayGoalKeeper : String = ""
    private var homeDefense : String = ""
    private var awayDefense : String = ""
    private var homeMidfield : String = ""
    private var awayMidfield : String = ""
    private var homeForward : String = ""
    private var awayForward : String = ""
    private var homeSubtitutes : String = ""
    private var awaySubtitutes: String = ""
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
        eventId = intent.getStringExtra("eventId")
        homeId = intent.getStringExtra("homeId")
        awayId = intent.getStringExtra("awayId")

        showActionBar()
        val request = ApiRepository()
        val gson = Gson()
        presenter = DetailPresenter(this, request, gson)
        presenter.getTeamDetail(eventId, homeId, awayId)

    } //==========================================================================================



    // ============================= function ====================================================
    private fun DefaultData(){

        /*homeName = intent.getStringExtra("homeName")
        awayName = intent.getStringExtra("awayName")
        homeId = intent.getStringExtra("homeId")
        awayId = intent.getStringExtra("awayId")
        eventDate = intent.getStringExtra("date")*/
        dateventTV.text = eventDate
        homenameTV.text = homeName
        awaynameTV.text = awayName
    }

    private fun showActionBar(){
        val actionbar = supportActionBar
        actionbar!!.title = "Detail"
        actionbar.setDisplayHomeAsUpEnabled(true)
    }

    override fun showhomeImage(data: List<Team>) {
        homeTeamObj = data[0]
        homeBadge = homeTeamObj.teamBadge
        Glide.with(this).load(homeBadge).into(homeImage)

    }

    override fun showawayImage(data: List<Team>) {
        awayTeamObj = data[0]
        awayBadge = awayTeamObj.teamBadge
        Glide.with(this).load(awayBadge).into(awayImage)
    }

    override fun showDetail(data: List<Event>) {
        eventdetail = Event(data[0].homeName,
                data[0].awayName,
                data[0].eventDate,
                data[0].homeScore,
                data[0].awayScore,
                data[0].homeGoalDetails,
                data[0].awayGoalDetails,
                data[0].homeGoalKeeper,
                data[0].awayGoalKeeper,
                data[0].homeShots.toString(),
                data[0].awayShots,
                data[0].awayDefense,
                data[0].homeMidfield,
                data[0].awayMidfield,
                data[0].homeForward,
                data[0].awayForward,
                data[0].homeSubtitutes,
                data[0].awaySubtitutes)

        dateventTV.text = data[0].eventDate
        homenameTV.text = data[0].homeName
        awaynameTV.text = data[0].awayName

        if(intent.getStringExtra("homeGoal")!=null)
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

                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }



}

