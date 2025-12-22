package com.example.a18112025

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class StudentViewModel : ViewModel() {
    // Sử dụng LiveData để Fragment tự động cập nhật giao diện khi dữ liệu thay đổi
    private val _studentList = MutableLiveData<MutableList<Student>>()
    val studentList: LiveData<MutableList<Student>> = _studentList

    init {
        // Dữ liệu mẫu ban đầu
        _studentList.value = mutableListOf(
            Student("20200001", "Nguyễn Văn A", "0901234567", "Hà Nội"),
            Student("20200002", "Trần Thị B", "0987654321", "TP.HCM")
        )
    }

    fun addStudent(student: Student) {
        val currentList = _studentList.value ?: mutableListOf()
        currentList.add(student)
        _studentList.value = currentList // Cập nhật LiveData
    }

    fun updateStudent(position: Int, newInfo: Student) {
        val currentList = _studentList.value ?: return
        if (position in currentList.indices) {
            currentList[position] = newInfo
            _studentList.value = currentList
        }
    }

    fun deleteStudent(position: Int) {
        val currentList = _studentList.value ?: return
        if (position in currentList.indices) {
            currentList.removeAt(position)
            _studentList.value = currentList
        }
    }
}