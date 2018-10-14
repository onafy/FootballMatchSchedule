package onafy.footballmatchschedule.Features.Favorites

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import onafy.footballmatchschedule.DBLokal.Favorite
import onafy.footballmatchschedule.R

import org.jetbrains.anko.find

class FavoriteAdapter(private val context: Context, private val favorite: List<Favorite>, val listener: (Favorite) -> Unit) : RecyclerView.Adapter<FavoriteViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            FavoriteViewHolder(LayoutInflater.from(context).inflate(R.layout.favoritelist_item, parent, false))


    override fun getItemCount(): Int = favorite.size

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bindItem(favorite[position], listener)
    }

}

class FavoriteViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val homeName: TextView = view.find(R.id.homenameTV)
    private val eventDate: TextView = view.find(R.id.dateeventTV)
    private val homeScore: TextView = view.find(R.id.homescoreTV)
    private val awayScore: TextView = view.find(R.id.awayscoreTV)
    private val awayName: TextView = view.find(R.id.awaynameTV)

    fun bindItem(favorite: Favorite, listener: (Favorite) -> Unit) {
        homeName.text = favorite.homeName
        awayName.text = favorite.awayName
        Log.d(favorite.homeName, "HomeName6")
        Log.d(favorite.awayName, "awayName7")
        eventDate.text = favorite.eventDate
        homeScore.text = favorite.homeScore
        awayScore.text = favorite.awayScore
        itemView.setOnClickListener {
            listener(favorite)
        }
    }

}



