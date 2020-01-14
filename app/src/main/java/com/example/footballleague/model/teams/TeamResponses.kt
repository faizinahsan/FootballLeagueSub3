package com.example.footballleague.model.teams

import com.google.gson.annotations.SerializedName

data class TeamResponses(
    @SerializedName("teams") val teams : List<Teams>
)