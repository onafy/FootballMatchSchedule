package onafy.footballmatchschedule.Features.Detail

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.google.gson.Gson
import kotlinx.android.synthetic.main.detail_pastmatch.*
import onafy.footballmatchschedule.Api.ApiRepository
import onafy.footballmatchschedule.ModelDataClass.Team
import onafy.footballmatchschedule.R
import onafy.footballmatchschedule.Util.invisible
import onafy.footballmatchschedule.Util.visible

class DetailActivity : AppCompatActivity(), DetailView {
    //=================================== declaration ===========================================
    private var teams: MutableList<Team> = mutableListOf()
    private lateinit var homeTeamObj: Team
    private lateinit var awayTeamObj: Team
    private lateinit var presenter: DetailPresenter
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
    //=============================================================================================

    //============================= main =========================================================
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_pastmatch)
        showActionBar()
        DefaultData()
        val request = ApiRepository()
        val gson = Gson()
        presenter = DetailPresenter(this, request, gson)
        presenter.getTeamDetail(homeId, awayId)

       if(intent.getStringExtra("homeGoal")!=null)
        {
            AdditionalData()
        }
    } //==========================================================================================



    // ============================= function ====================================================
    private fun DefaultData(){
        homeName = intent.getStringExtra("homeName")
        awayName = intent.getStringExtra("awayName")
        homeId = intent.getStringExtra("homeId")
        awayId = intent.getStringExtra("awayId")
        eventDate = intent.getStringExtra("date")
        dateventTV.text = eventDate
        homenameTV.text = homeName
        awaynameTV.text = awayName
    }

    private fun showActionBar(){
        val actionbar = supportActionBar
        actionbar!!.title = "Detail"
        actionbar.setDisplayHomeAsUpEnabled(true)
    }

    private fun AdditionalData(){
        homeScore = intent.getStringExtra("homeScore")
        awayScore = intent.getStringExtra("awayScore")
        homeGoals = intent.getStringExtra("homeGoal")
        awayGoals = intent.getStringExtra("awayGoal")
        homeShots = intent.getStringExtra("homeShots")
        awayShots = intent.getStringExtra("awayShots")
        homeGoalKeeper = intent.getStringExtra("homeGoalKeeper")
        awayGoalKeeper = intent.getStringExtra("awayGoalKeeper")
        homeDefense = intent.getStringExtra("homeDefense")
        awayDefense = intent.getStringExtra("awayDefense")
        homeMidfield = intent.getStringExtra("homeMidfield")
        awayMidfield = intent.getStringExtra("awayMidfield")
        homeForward = intent.getStringExtra("homeForward")
        awayForward = intent.getStringExtra("awayForward")
        homeSubtitutes = intent.getStringExtra("homeSubtitutes")
        awaySubtitutes = intent.getStringExtra("awaySubtitutes")


        homescoreTV.text = homeScore
        awayscoreTV.text = awayScore
        homegoalsTV.text = homeGoals
        awaygoalsTV.text = awayGoals
        awaygoalkeeperTV.text = awayGoalKeeper
        homegoalkeeperTV.text = homeGoalKeeper
        homeshotsTV.text = homeShots
        awayshotsTV.text = awayShots
        homedefenseTV.text = homeDefense
        awaydefenseTV.text = awayDefense
        homemidfieldTV.text = homeMidfield
        awaymidfieldTV.text = awayMidfield
        homeforwardTV.text = homeForward
        awayforwardTV.text = awayForward
        homesubtitutesTV.text = homeSubtitutes
        awaysubtitutesTV.text = awaySubtitutes
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
}

