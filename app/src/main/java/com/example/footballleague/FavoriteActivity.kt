package com.example.footballleague

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.footballleague.adapter.FavMatchAdapter
import com.example.footballleague.model.favorites.FavNextMatchTable
import com.example.footballleague.utils.database.FavDatabase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_favorite.*
import kotlin.collections.ArrayList

class FavoriteActivity : AppCompatActivity() {
    private var items: ArrayList<FavNextMatchTable> = ArrayList()

    private var mDb: FavDatabase? = null
    val compositeDisposable = CompositeDisposable()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        mDb = FavDatabase.getInstance(this)


        fetchFavFromDB()

    }

    private fun fetchFavFromDB() {
    compositeDisposable.add(mDb!!.matchDAO().getNextMatchAll()
        .subscribeOn(Schedulers.computation())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe{
            showRecyclerCardView(it)
        })
    }
    private fun showRecyclerCardView(matchList:List<FavNextMatchTable>) {
        fav_match_rv.layoutManager = LinearLayoutManager(this)
        val matchAdapter = FavMatchAdapter(matchList as ArrayList<FavNextMatchTable>)
        matchAdapter.notifyDataSetChanged()
        fav_match_rv.adapter = matchAdapter
    }

    override fun onDestroy() {
        super.onDestroy()
        FavDatabase.destroyInstance()
        compositeDisposable.dispose()
    }
}
