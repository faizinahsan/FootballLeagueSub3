package com.example.footballleague

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.ImageView
import android.widget.TextView
import com.example.footballleague.model.leagues.Leagues
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.detail_activity.*
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.nestedScrollView

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setContentView(R.layout.detail_activity)
        val league_name = intent.getStringExtra("leagues_name")
        val league_desc= intent.getStringExtra("leagues_desc")
        val league_image = intent.getStringExtra("leagues_image")
        id_nama_liga_detail.text = league_name
        id_desc_liga_detail.text = league_desc
        Picasso.get().load(league_image).into(id_gambar_liga_detail)
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
