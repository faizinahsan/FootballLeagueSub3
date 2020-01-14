package com.example.footballleague

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.footballleague.api.ApiMainService
import com.example.footballleague.model.events.EventResponses
import com.example.footballleague.model.events.Events
import com.example.footballleague.model.events.EventsResponses
import com.example.footballleague.model.favorites.FavNextMatchTable
import com.example.footballleague.model.teams.TeamResponses
import com.example.footballleague.utils.database.FavDatabase
import com.squareup.picasso.Picasso
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_detail_match.*
import retrofit2.Call
import retrofit2.Response


class DetailMatchActivity : AppCompatActivity() {
    private var mDb: FavDatabase? = null
    val compositeDisposable = CompositeDisposable()

    private var menu: Menu? = null

    private val eventItem: ArrayList<Events> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_match)
        mDb = FavDatabase.getInstance(this)

        val idAdapter = intent.getIntExtra("id_adapter",0)
        if (idAdapter==1){
            bindRetrofitData()
            add_to_fav.text = "Add To Favorites"
        }else{
            bindSQLData()
            add_to_fav.text = "Delete From Favorites"
        }


    }
    private fun bindRetrofitData(){
        val getId = intent?.getIntExtra("id_match",0)
        if (getId != null) {
            ApiMainService().service.getMatchbyId(getId).enqueue(object :retrofit2.Callback<EventsResponses>{
                override fun onFailure(call: Call<EventsResponses>, t: Throwable) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onResponse(
                    call: Call<EventsResponses>,
                    response: Response<EventsResponses>
                ) {
                    id_event_name_detail.text = response.body()?.events?.get(0)?.strEvent
                    id_home_team_detail.text = response.body()?.events?.get(0)?.strHomeTeam
                    id_away_team_detail.text = response.body()?.events?.get(0)?.strAwayTeam
                    id_home_score_detail.text = response.body()?.events?.get(0)?.intHomeScore
                    id_away_score_detail.text = response.body()?.events?.get(0)?.intAwayScore
                    id_round_detail.text = response.body()?.events?.get(0)?.intRound.toString()
                    Log.d("Id Home Team", response.body()?.events?.get(0)?.idHomeTeam.toString())
                    Log.d("Id Away Team", response.body()?.events?.get(0)?.idAwayTeam.toString())
                    response.body()?.events?.get(0)?.idHomeTeam?.let { getHomeLogoTeam(it) }
                    response.body()?.events?.get(0)?.idAwayTeam?.let { getAwayLogoTeam(it) }
                    id_date_event_detail.text = response.body()?.events?.get(0)?.strDate
                    id_time_event_detail.text = response.body()?.events?.get(0)?.strTime
                    var match = response.body()?.events?.get(0)
                    var favMatch = match?.let {
                        FavNextMatchTable(
                            null, it.idEvent,it.strEvent,it.strHomeTeam,it.strAwayTeam,
                            it.intHomeScore,it.intRound,it.intAwayScore,it.strDate,it.strTime,
                            it.idHomeTeam,it.idAwayTeam,it.strThumb
                        )
                    }
                    add_to_fav.setOnClickListener{
                        favMatch?.let { insertToFavNextMatch(nextMatch = it) }
                        Toast.makeText(baseContext,"Match Has Been Added to Favorites",Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }

            })
        }
    }
    private fun bindSQLData(){
        val nextMatch = intent.getParcelableExtra<FavNextMatchTable>("id_match")
        id_event_name_detail.text = nextMatch.strEvent
        id_home_team_detail.text = nextMatch.strHomeTeam
        id_away_team_detail.text = nextMatch.strAwayTeam
        id_home_score_detail.text = nextMatch.intHomeScore
        id_away_score_detail.text = nextMatch.intAwayScore
        id_round_detail.text = nextMatch.intRound.toString()
        Log.d("Id Home Team", nextMatch.idHomeTeam.toString())
        Log.d("Id Away Team", nextMatch.idAwayTeam.toString())
        nextMatch.idHomeTeam.let { getHomeLogoTeam(it) }
        nextMatch.idAwayTeam.let { getAwayLogoTeam(it) }
        id_date_event_detail.text = nextMatch.strDate
        id_time_event_detail.text = nextMatch.strTime
        add_to_fav.setOnClickListener {
            nextMatch?.let { deleteFromFav(nextMatch = it) }
            Toast.makeText(baseContext,"Match Has Been Deleted from Favorites",Toast.LENGTH_SHORT).show()
            finish()
        }
    }
    private fun insertToFavNextMatch(nextMatch: FavNextMatchTable) {
        compositeDisposable.add(Observable.fromCallable{mDb?.matchDAO()?.insertNextMatch(nextMatch)}
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe())
    }
    private fun deleteFromFav(nextMatch: FavNextMatchTable){
        compositeDisposable.add(Observable.fromCallable{mDb?.matchDAO()?.deleteNextMatch(nextMatch)}
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe())
    }
//    private fun insertToFavPrevMatch(prevMatch: FavPreviousMatchTable) {
//        val task = Runnable { mDb?.matchDAO()?.insertPrevMatch(prevMatch) }
//        mDbWorkerThread.postTask(task)
//    }
    private fun getHomeLogoTeam(idTeam:Int){
        ApiMainService().service.getTeamById(idTeam).enqueue(object :retrofit2.Callback<TeamResponses>{
            override fun onFailure(call: Call<TeamResponses>, t: Throwable) {

            }

            override fun onResponse(call: Call<TeamResponses>, response: Response<TeamResponses>) {
                Picasso.get().load(response.body()?.teams?.get(0)?.strTeamBadge).into(id_logo_home_detail)
            }
        })
    }
    private fun getAwayLogoTeam(idTeam:Int){
        ApiMainService().service.getTeamById(idTeam).enqueue(object :retrofit2.Callback<TeamResponses>{
            override fun onFailure(call: Call<TeamResponses>, t: Throwable) {

            }

            override fun onResponse(call: Call<TeamResponses>, response: Response<TeamResponses>) {
                Picasso.get().load(response.body()?.teams?.get(0)?.strTeamBadge).into(id_logo_away_detail)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        this.menu = menu
        val inflater = menuInflater
        inflater.inflate(R.menu.option_match_menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.fav_btn_menu -> {
                Toast.makeText(this,"FAV HAS BEEN CLICKED",Toast.LENGTH_SHORT).show()
                menu?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_star_not_hollow)
                return true
            }
            else -> return true
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        FavDatabase.destroyInstance()
        compositeDisposable.dispose()
    }
}
