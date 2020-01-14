package com.example.footballleague.utils.database

import android.content.Context
import androidx.annotation.NonNull
import androidx.annotation.VisibleForTesting
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.footballleague.model.favorites.FavNextMatchTable
import com.example.footballleague.utils.dao.FavMatchDAO


@Database(entities = [FavNextMatchTable::class], version = 1)
abstract class FavDatabase: RoomDatabase() {
    abstract fun matchDAO(): FavMatchDAO
    companion object {
        private var INSTANCE: FavDatabase? = null
//        @VisibleForTesting
//        val MIGRATION_1_2: Migration = object : Migration(1, 2) {
//            override fun migrate(@NonNull database: SupportSQLiteDatabase) { // Room uses an own database hash to uniquely identify the database
//// Since version 1 does not use Room, it doesn't have the database hash associated.
//// By implementing a Migration class, we're telling Room that it should use the data
//// from version 1 to version 2.
//// If no migration is provided, then the tables will be dropped and recreated.
//// Since we didn't alter the table, there's nothing else to do here.
//                database.execSQL("ALTER TABLE fav_next_match_table "
//                        +"ADD COLUMN address TEXT")
//            }
//        }

        fun getInstance(context: Context): FavDatabase? {
            if (INSTANCE == null) {
                synchronized(FavDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        FavDatabase::class.java, "favdata.db")
                        .build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}