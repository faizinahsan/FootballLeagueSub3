package com.example.footballleague.model.leagues

import com.example.footballleague.model.leagues.Leagues
import com.google.gson.annotations.SerializedName

data class LeaguesResponses(
    @SerializedName("leagues") val leagues : List<Leagues>
)