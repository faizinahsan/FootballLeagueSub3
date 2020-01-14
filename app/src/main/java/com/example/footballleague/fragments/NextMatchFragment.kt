package com.example.footballleague.fragments


import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.footballleague.MainActivity
import com.example.footballleague.R
import com.example.footballleague.adapter.MatchAdapter
import com.example.footballleague.api.ApiMainService
import com.example.footballleague.model.events.Events
import com.example.footballleague.model.events.EventsResponses
import kotlinx.android.synthetic.main.fragment_next_match.*
import retrofit2.Call
import retrofit2.Response


/**
 * A simple [Fragment] subclass.
 */
class NextMatchFragment : Fragment(),View.OnClickListener,MainActivity.OnAboutDataReceivedListener {
    private var items: ArrayList<Events> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_next_match, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mActivity = activity as MainActivity?
        mActivity?.setAboutDataListener(this)

    }
    private fun showRecyclerCardView(matchList:ArrayList<Events>) {
        next_match_rv.layoutManager = LinearLayoutManager(context)
        val matchAdapter = MatchAdapter(matchList)
        matchAdapter.notifyDataSetChanged()
        next_match_rv.adapter = matchAdapter
    }
    override fun onClick(v: View?) {
    }

    override fun onDataReceived(idLeague: Int?) {
        Log.d("onDataReceived",idLeague.toString())
        if (idLeague != null) {
            ApiMainService().service.getNextMatchById(idLeague).enqueue(object :retrofit2.Callback<EventsResponses>{
                override fun onFailure(call: Call<EventsResponses>, t: Throwable) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                @SuppressLint("LongLogTag")
                override fun onResponse(
                    call: Call<EventsResponses>,
                    response: Response<EventsResponses>
                ) {
                    Log.d("onDataReceived ,onResponse",idLeague.toString())
                    items.clear()
                    response.body()?.events?.let { items.addAll(it) }
                    showRecyclerCardView(items)
                }

            })
        }
    }


}
