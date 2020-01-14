package com.example.footballleague.fragments


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment

import com.example.footballleague.R
import kotlinx.android.synthetic.main.fragment_league_dialog.*

/**
 * A simple [Fragment] subclass.
 */
class LeagueDialogFragment : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_league_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (arguments !=null){
            val mArgs = arguments
            val leagueName= mArgs?.getString("league_name")
            val leagueDesc= mArgs?.getString("league_desc")
            Log.d("Nama Liga:",leagueName)
            Log.d("Desc Liga:",leagueDesc)
            league_name_dialog.text = leagueName
            league_desc_dialog.text = leagueDesc
        }
        close_dialog.setOnClickListener(object: View.OnClickListener {
            override fun onClick(p0: View?) {
                dialog?.cancel()
            }
        })
    }


}
