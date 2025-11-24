package com.example.playstore

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AppVerticalAdapter(
    private val apps: List<App>
) : RecyclerView.Adapter<AppVerticalAdapter.AppViewHolder>() {

    inner class AppViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivIcon: ImageView = itemView.findViewById(R.id.ivAppIcon)
        private val tvName: TextView = itemView.findViewById(R.id.tvAppName)
        private val tvCategory: TextView = itemView.findViewById(R.id.tvCategory)
        private val tvRating: TextView = itemView.findViewById(R.id.tvRating)
        private val tvSize: TextView = itemView.findViewById(R.id.tvSize)

        fun bind(app: App) {
            ivIcon.setImageResource(app.iconRes)
            tvName.text = app.name
            tvCategory.text = app.category
            tvRating.text = app.rating
            tvSize.text = app.size
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_app_vertical, parent, false)
        return AppViewHolder(view)
    }

    override fun onBindViewHolder(holder: AppViewHolder, position: Int) {
        holder.bind(apps[position])
    }

    override fun getItemCount() = apps.size
}