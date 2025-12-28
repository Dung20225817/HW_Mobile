package com.example.a18112025

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

// Đổi ViewModel thành AndroidViewModel để lấy được Application Context
class StudentViewModel(application: Application) : AndroidViewModel(application) {

    private val _studentList = MutableLiveData<MutableList<Student>>()
    val studentList: LiveData<MutableList<Student>> = _studentList

    // Khởi tạo Database Helper
    private val dbHelper = StudentDatabaseHelper(application)

    init {
        // Load dữ liệu từ SQLite ngay khi App mở lên
        loadStudentsFromDb()
    }

    // Hàm load lại dữ liệu từ DB lên UI
    private fun loadStudentsFromDb() {
        val list = dbHelper.getAllStudents()
        _studentList.value = list
    }

    fun addStudent(student: Student) {
        dbHelper.addStudent(student)
        loadStudentsFromDb() // Load lại để UI cập nhật
    }

    fun updateStudent(position: Int, newInfo: Student) {
        // Với SQLite, ta update dựa trên ID (MSSV) chứ không phải position
        // Tuy nhiên UI của bạn đang trả về position, ta dùng nó để lấy ID cũ nếu cần
        // Nhưng ở form update, studentId đã nằm trong newInfo rồi.
        dbHelper.updateStudent(newInfo)
        loadStudentsFromDb()
    }

    fun deleteStudent(position: Int) {
        // Lấy ID của sinh viên tại vị trí position để xóa trong DB
        val list = _studentList.value
        if (list != null && position in list.indices) {
            val studentIdToDelete = list[position].studentId
            dbHelper.deleteStudent(studentIdToDelete)
            loadStudentsFromDb()
        }
    }
}