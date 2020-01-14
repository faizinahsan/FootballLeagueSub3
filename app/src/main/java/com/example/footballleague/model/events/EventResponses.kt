package com.example.footballleague.model.events

import com.google.gson.annotations.SerializedName


data class EventResponses (
    @SerializedName("event") val event : List<Events>
    )