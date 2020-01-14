package com.example.footballleague.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.footballleague.DetailMatchActivity
import com.example.footballleague.R
import com.example.footballleague.model.events.Events
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.match_item_view.view.*

class MatchAdapter(private val listMatch: ArrayList<Events>) : RecyclerView.Adapter<MatchAdapter.ViewHolder>() {
    class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
        fun bind(events: Events){
            with(itemView) {
                item_match_name.text = events.strEvent
                Picasso.get().load(events.strThumb).into(item_match_image)
                match_event_card.setOnClickListener {
                    val intent = Intent(context,DetailMatchActivity::class.java)
                    intent.putExtra("id_match",events.idEvent)
                    intent.putExtra("id_adapter",1)
                    context.startActivity(intent)
                    Toast.makeText(itemView.context, "Favorite ${events.idEvent}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    interface CallbackInterface {
        fun passDataCallback(message: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.match_item_view, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listMatch.size
    }

    override fun onBindViewHolder(holder: MatchAdapter.ViewHolder, position: Int) {
        holder.bind(listMatch[position])
    }
}