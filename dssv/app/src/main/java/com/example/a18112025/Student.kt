package com.example.a18112025

import java.io.Serializable

data class Student(
    var studentId: String = "",
    var name: String = "",
    var phoneNumber: String = "",
    var address: String = ""
) : Serializable