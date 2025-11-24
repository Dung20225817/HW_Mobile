package com.example.emailui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: EmailAdapter
    private val emailList = mutableListOf<Email>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupToolbar()
        setupRecyclerView()
        loadSampleData()
        setupFab()
    }

    private fun setupToolbar() {
        supportActionBar?.title = "Inbox"
    }

    private fun setupRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView)
        adapter = EmailAdapter(emailList) { position ->
            // Handle item click
        }
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private fun setupFab() {
        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener {
            // Handle compose email
        }
    }

    private fun loadSampleData() {
        emailList.addAll(
            listOf(
                Email(
                    "E",
                    "#4285F4",
                    "Edurila.com",
                    "$19 Only (First 10 spots) - Bestselling...",
                    "Are you looking to Learn Web Designin...",
                    "12:34 PM",
                    false
                ),
                Email(
                    "C",
                    "#EA4335",
                    "Chris Abad",
                    "Help make Campaign Monitor better",
                    "Let us know your thoughts! No Images...",
                    "11:22 AM",
                    false
                ),
                Email(
                    "T",
                    "#34A853",
                    "Tuto.com",
                    "8h de formation gratuite et les nouvea...",
                    "Photoshop, SEO, Blender, CSS, WordPre...",
                    "11:04 AM",
                    false
                ),
                Email(
                    "S",
                    "#9E9E9E",
                    "support",
                    "Société Ovh : suivi de vos services - hp...",
                    "SAS OVH - http://www.ovh.com 2 rue K...",
                    "10:26 AM",
                    false
                ),
                Email(
                    "M",
                    "#8BC34A",
                    "Matt from Ionic",
                    "The New Ionic Creator Is Here!",
                    "Announcing the all-new Creator, built...",
                    "9:45 AM",
                    false
                )
            )
        )
        adapter.notifyDataSetChanged()
    }
}