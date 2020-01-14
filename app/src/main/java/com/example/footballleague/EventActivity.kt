package com.example.footballleague

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.footballleague.adapter.MatchAdapter
import com.example.footballleague.api.ApiMainService
import com.example.footballleague.model.events.EventResponses
import com.example.footballleague.model.events.Events
import com.example.footballleague.model.events.EventsResponses
import kotlinx.android.synthetic.main.activity_event.*
import kotlinx.android.synthetic.main.fragment_next_match.*
import retrofit2.Call
import retrofit2.Response

class EventActivity : AppCompatActivity() {
    private var items: ArrayList<Events> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event)
        val getQuery = intent.extras?.getString("search_key")
        Log.d("Query Event:",getQuery)
        if (getQuery != null) {
            ApiMainService().service.getMatchbyQuery(getQuery).enqueue(object :retrofit2.Callback<EventResponses>{
                override fun onFailure(call: Call<EventResponses>, t: Throwable) {
                    Log.d("onFailure Search",t.toString())
                }

                override fun onResponse(
                    call: Call<EventResponses>,
                    response: Response<EventResponses>
                ) {
                    items.clear()
                    response.body()?.event?.filter { it.strSport =="Soccer" }?.let { items.addAll(it) }
//                    response.body()?.event?.let { items.addAll(it) }
                    showRecyclerCardView(items)
                }

            })
        }
    }
    private fun showRecyclerCardView(matchList:ArrayList<Events>) {
        search_event_rv.layoutManager = LinearLayoutManager(this)
        val matchAdapter = MatchAdapter(matchList)
        matchAdapter.notifyDataSetChanged()
        search_event_rv.adapter = matchAdapter
    }
}
