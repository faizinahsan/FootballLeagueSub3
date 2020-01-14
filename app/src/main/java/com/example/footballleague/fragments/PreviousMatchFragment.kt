package com.example.footballleague.fragments


import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.footballleague.MainActivity

import com.example.footballleague.R
import com.example.footballleague.adapter.MatchAdapter
import com.example.footballleague.api.ApiMainService
import com.example.footballleague.model.events.Events
import com.example.footballleague.model.events.EventsResponses
import kotlinx.android.synthetic.main.fragment_next_match.*
import kotlinx.android.synthetic.main.fragment_previous_match.*
import retrofit2.Call
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 */
class PreviousMatchFragment : Fragment(),MainActivity.OnAboutDataReceivedListener2 {
    private var items: ArrayList<Events> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_previous_match, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mActivity = activity as MainActivity?
        mActivity?.setAboutDataListener2(this)

    }
    private fun showRecyclerCardView(matchList:ArrayList<Events>) {
        previous_match_rv.layoutManager = LinearLayoutManager(context)
        val matchAdapter = MatchAdapter(matchList)
        matchAdapter.notifyDataSetChanged()
        previous_match_rv.adapter = matchAdapter
    }

    override fun onDataReceived(idLeague: Int?) {
        Log.d("Previous",idLeague.toString())
        if (idLeague != null) {
            ApiMainService().service.getPreviousMatchById(idLeague).enqueue(object :retrofit2.Callback<EventsResponses>{
                override fun onFailure(call: Call<EventsResponses>, t: Throwable) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                @SuppressLint("LongLogTag")
                override fun onResponse(
                    call: Call<EventsResponses>,
                    response: Response<EventsResponses>
                ) {
                    Log.d("Previous onResponse",idLeague.toString())
                    items.clear()
                    response.body()?.events?.let { items.addAll(it) }
                    showRecyclerCardView(items)
                }

            })
        }
    }


}
