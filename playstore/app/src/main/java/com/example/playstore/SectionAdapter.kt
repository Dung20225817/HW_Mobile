package com.example.playstore

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SectionAdapter(
    private val sections: List<Section>
) : RecyclerView.Adapter<SectionAdapter.SectionViewHolder>() {

    inner class SectionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvTitle: TextView = itemView.findViewById(R.id.tvSectionTitle)
        private val ivMore: ImageView = itemView.findViewById(R.id.ivMore)
        private val recyclerView: RecyclerView = itemView.findViewById(R.id.rvApps)

        fun bind(section: Section) {
            tvTitle.text = section.title

            when (section.type) {
                SectionType.VERTICAL_LIST -> {
                    setupVerticalList(section.apps)
                }
                SectionType.HORIZONTAL_GRID -> {
                    setupHorizontalGrid(section.apps)
                }
            }
        }

        private fun setupVerticalList(apps: List<App>) {
            val adapter = AppVerticalAdapter(apps)
            recyclerView.layoutManager = LinearLayoutManager(itemView.context)
            recyclerView.adapter = adapter
            recyclerView.isNestedScrollingEnabled = false
        }

        private fun setupHorizontalGrid(apps: List<App>) {
            val adapter = AppHorizontalAdapter(apps)
            recyclerView.layoutManager = LinearLayoutManager(
                itemView.context,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            recyclerView.adapter = adapter
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SectionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_section, parent, false)
        return SectionViewHolder(view)
    }

    override fun onBindViewHolder(holder: SectionViewHolder, position: Int) {
        holder.bind(sections[position])
    }

    override fun getItemCount() = sections.size
}