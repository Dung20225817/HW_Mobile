package com.example.playstore

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SectionAdapter
    private val sectionList = mutableListOf<Section>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupRecyclerView()
        loadSampleData()
    }

    private fun setupRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView)
        adapter = SectionAdapter(sectionList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private fun loadSampleData() {
        // Sponsored - Suggested for you section
        val sponsoredApps = listOf(
            App(
                "Mech Assembler: Zombie Swarm",
                "Action • Role Playing • Roguelike • Zombie",
                "4.8★",
                "624 MB",
                R.drawable.app
            ),
            App(
                "MU: Hồng Hoá Đạo",
                "Role Playing",
                "4.8★",
                "339 MB",
                R.drawable.app
            ),
            App(
                "War Inc: Rising",
                "Strategy • Tower defense",
                "4.9★",
                "231 MB",
                R.drawable.app
            )
        )

        sectionList.add(
            Section(
                "Sponsored • Suggested for you",
                sponsoredApps,
                SectionType.VERTICAL_LIST
            )
        )

        // Recommended for you section
        val recommendedApps = listOf(
            App(
                "Suno - AI Music &",
                "",
                "",
                "",
                R.drawable.app
            ),
            App(
                "Claude by",
                "",
                "",
                "",
                R.drawable.app
            ),
            App(
                "DramaBox -",
                "",
                "",
                "",
                R.drawable.app
            ),
            App(
                "Pinterest",
                "",
                "",
                "",
                R.drawable.app
            )
        )

        sectionList.add(
            Section(
                "Recommended for you",
                recommendedApps,
                SectionType.HORIZONTAL_GRID
            )
        )

        adapter.notifyDataSetChanged()
    }
}