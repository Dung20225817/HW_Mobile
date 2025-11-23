package com.example.emailui

data class Email(
    val avatar: String,
    val avatarColor: String,
    val sender: String,
    val subject: String,
    val preview: String,
    val time: String,
    val isStarred: Boolean
)