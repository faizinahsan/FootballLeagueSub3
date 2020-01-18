package com.example.footballleague

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.example.footballleague.adapter.MatchSectionAdapter
import com.example.footballleague.api.ApiMainService
import com.example.footballleague.model.leagues.Leagues
import com.example.footballleague.model.leagues.LeaguesResponses
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.detail_liga_layout.*
import retrofit2.Call
import retrofit2.Response


class MainActivity : AppCompatActivity(),AdapterView.OnItemSelectedListener{
    private var items: ArrayList<Leagues> = ArrayList()
    private var mAboutDataListener: OnAboutDataReceivedListener? = null

    interface OnAboutDataReceivedListener {
        fun onDataReceived(idLeague: Int?)
    }

    fun setAboutDataListener(listener: OnAboutDataReceivedListener?) {
        mAboutDataListener = listener
    }
    private var mAboutDataListener2: OnAboutDataReceivedListener2? = null

    interface OnAboutDataReceivedListener2 {
        fun onDataReceived(idLeague: Int?)
    }

    fun setAboutDataListener2(listener: OnAboutDataReceivedListener2?) {
        mAboutDataListener2 = listener
    }

    var spinner: Spinner? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        spinner = this.leagues_spinner
        spinner!!.onItemSelectedListener = this

        setViewpagerAdapter()
        ApiMainService().service.getLeagues().enqueue(object :retrofit2.Callback<LeaguesResponses>{
            override fun onFailure(call: Call<LeaguesResponses>, t: Throwable) {
                Log.d("GAGAL","Mengambil data")
                Log.d("Pesan GAGAL",t.toString())
            }

            override fun onResponse(
                    call: Call<LeaguesResponses>,
                    response: Response<LeaguesResponses>
            ) {
                if (response.code()==200){
                    items.clear()
                    response.body()?.leagues?.filter { it.strSport=="Soccer" }?.let { items.addAll(it) }
//                    showRecyclerList(items)
                    // Create an ArrayAdapter using a simple spinner layout and languages array
                    setSpinnerAdapter(items)
                }
            }

        })

//        leagues_rv.setHasFixedSize(true)


    }
    private fun setSpinnerAdapter(spinnerItems: ArrayList<Leagues>){
        try {
            var itemNames:ArrayList<String> = ArrayList()
            for (item in spinnerItems){
                itemNames.add(item.strLeague)
            }
            val aa = ArrayAdapter(this, android.R.layout.simple_spinner_item, itemNames)
            // Set layout to use when the list of choices appear
            aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Set Adapter to Spinner
            spinner!!.adapter = aa

        }catch(e:Exception) {
            e.message
        }
    }
    override fun onNothingSelected(p0: AdapterView<*>?) {
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        getDetailLeague(items[p2].idLeague)
        Log.d("OnItemSelected ID",items[p2].idLeague.toString())
        mAboutDataListener?.onDataReceived(items[p2].idLeague)
        mAboutDataListener2?.onDataReceived(items[p2].idLeague)
    }

    private fun setViewpagerAdapter(){
        val sectionsPagerAdapter = MatchSectionAdapter(this, supportFragmentManager)
        match_view_pager.adapter = sectionsPagerAdapter
        match_tab_layout.setupWithViewPager(match_view_pager)
        supportActionBar?.elevation = 0f
    }
    private fun getDetailLeague(idLeague:Int) {
//        Log.d("Detail JSON",idLeague.toString())
        ApiMainService().service.getLeagueById(idLeague).enqueue(object :retrofit2.Callback<LeaguesResponses>{
            override fun onFailure(call: Call<LeaguesResponses>, t: Throwable) {
                Log.d("GAGAL","GAGAL AMBIL DATA")
            }

            override fun onResponse(call: Call<LeaguesResponses>, response: Response<LeaguesResponses>) {
                if (response.code()==200){
                    nama_detail_liga.text = response.body()?.leagues?.get(0)?.strLeague
                    Picasso.get().load(response.body()?.leagues?.get(0)?.strBadge).into(gambar_detail_liga)
                    response.body()?.leagues?.get(0)?.let { toDetailActivity(it) }
                }
            }
        })
    }
    private fun toDetailActivity(items:Leagues) {
        more_info.setOnClickListener {
            val moveObjects = Intent(this@MainActivity,DetailActivity::class.java)
            moveObjects.putExtra("leagues_name",items.strLeague)
            moveObjects.putExtra("leagues_desc",items.strDescriptionEN)
            moveObjects.putExtra("leagues_image",items.strBadge)
            startActivity(moveObjects)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.search)?.actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                Toast.makeText(this@MainActivity, query, Toast.LENGTH_SHORT).show()
                val searchIntent = Intent(this@MainActivity,EventActivity::class.java)
                searchIntent.putExtra("search_key",query)
                startActivity(searchIntent)
                return true
            }
            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_fav -> {
                val moveToFavIntent = Intent(this,FavoriteActivity::class.java)
                startActivity(moveToFavIntent)
                return true
            }
            else -> return true
        }
    }
}
