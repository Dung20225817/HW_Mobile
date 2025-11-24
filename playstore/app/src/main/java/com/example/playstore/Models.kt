package com.example.playstore

data class App(
    val name: String,
    val category: String,
    val rating: String,
    val size: String,
    val iconRes: Int
)

data class Section(
    val title: String,
    val apps: List<App>,
    val type: SectionType
)

enum class SectionType {
    VERTICAL_LIST,
    HORIZONTAL_GRID
}