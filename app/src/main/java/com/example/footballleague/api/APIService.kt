package com.example.footballleague.api

import com.example.footballleague.model.events.EventResponses
import com.example.footballleague.model.events.Events
import com.example.footballleague.model.events.EventsResponses
import com.example.footballleague.model.leagues.Leagues
import com.example.footballleague.model.leagues.LeaguesResponses
import com.example.footballleague.model.teams.TeamResponses
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface APIService {
    @GET("api/v1/json/1/all_leagues.php")
    fun getLeagues() : Call<LeaguesResponses>
    @GET("api/v1/json/1/lookupleague.php")
    fun getLeagueById(@Query("id") idLeague:Int) : Call<LeaguesResponses>
    @GET("api/v1/json/1/eventsnextleague.php")
    fun getNextMatchById(@Query("id") idLeague:Int) : Call<EventsResponses>
    @GET("api/v1/json/1/eventspastleague.php")
    fun getPreviousMatchById(@Query("id") idLeague:Int) : Call<EventsResponses>
    @GET("api/v1/json/1/lookupevent.php")
    fun getMatchbyId(@Query("id") idEvent:Int) : Call<EventsResponses>
    @GET("api/v1/json/1/searchevents.php")
    fun getMatchbyQuery(@Query("e") eventsName:String) : Call<EventResponses>
    @GET("api/v1/json/1/lookupteam.php")
    fun getTeamById(@Query("id") teamId:Int) : Call<TeamResponses>
}