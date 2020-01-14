package com.example.footballleague.model.favorites

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "fav_next_match_table")
data class FavNextMatchTable(
    @PrimaryKey(autoGenerate = true) var id: Long?,
    @ColumnInfo(name="idEvent") val idEvent : Int,
    @ColumnInfo(name="strEvent") val strEvent : String,
    @ColumnInfo(name="strHomeTeam") val strHomeTeam : String,
    @ColumnInfo(name="strAwayTeam") val strAwayTeam : String,
    @ColumnInfo(name="intHomeScore") val intHomeScore : String?,
    @ColumnInfo(name="intRound") val intRound : Int?,
    @ColumnInfo(name="intAwayScore") val intAwayScore : String?,
    @ColumnInfo(name="strDate") val strDate : String,
    @ColumnInfo(name="strTime") val strTime : String,
    @ColumnInfo(name="idHomeTeam") val idHomeTeam : Int,
    @ColumnInfo(name="idAwayTeam") val idAwayTeam : Int,
    @ColumnInfo(name = "strThumb") val strThumb : String?

    ) : Parcelable