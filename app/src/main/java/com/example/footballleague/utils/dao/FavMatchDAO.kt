package com.example.footballleague.utils.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.footballleague.model.favorites.FavNextMatchTable
import io.reactivex.Flowable

@Dao
interface FavMatchDAO {
    @Query("SELECT * FROM fav_next_match_table")
    fun getNextMatchAll():Flowable<List<FavNextMatchTable>>

//    @Query("SELECT * FROM fav_previous_match_table")
//    fun getPrevMatchAll():List<FavPreviousMatchTable>

    @Insert(onConflict = REPLACE)
    fun insertNextMatch(nextMatch: FavNextMatchTable)

//    @Insert(onConflict = REPLACE)
//    fun insertPrevMatch(prevMatch: FavPreviousMatchTable)

    @Delete
    fun deleteNextMatch(nextMatch: FavNextMatchTable)

//    @Delete
//    fun deletePrevMatch(prevMatch: FavPreviousMatchTable)

}