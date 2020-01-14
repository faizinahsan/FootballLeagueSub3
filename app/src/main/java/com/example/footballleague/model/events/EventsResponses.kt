package com.example.footballleague.model.events

import com.google.gson.annotations.SerializedName

data class EventsResponses (
    @SerializedName("events") val events : List<Events>
)