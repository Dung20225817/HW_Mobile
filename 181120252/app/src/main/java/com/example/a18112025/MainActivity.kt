package com.example.a18112025

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var etStudentId: EditText
    private lateinit var etName: EditText
    private lateinit var btnAdd: Button
    private lateinit var btnUpdate: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: StudentAdapter
    private val studentList = mutableListOf<Student>()
    private var selectedPosition = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initViews()
        setupRecyclerView()
        setupButtons()
        loadSampleData()
    }

    private fun initViews() {
        etStudentId = findViewById(R.id.etStudentId)
        etName = findViewById(R.id.etName)
        btnAdd = findViewById(R.id.btnAdd)
        btnUpdate = findViewById(R.id.btnUpdate)
        recyclerView = findViewById(R.id.recyclerView)
    }

    private fun setupRecyclerView() {
        adapter = StudentAdapter(
            studentList,
            onDeleteClick = { position ->
                deleteStudent(position)
            },
            onItemClick = { position ->
                selectStudent(position)
            }
        )
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private fun setupButtons() {
        btnAdd.setOnClickListener {
            addStudent()
        }

        btnUpdate.setOnClickListener {
            updateStudent()
        }
    }

    private fun addStudent() {
        val name = etName.text.toString().trim()
        val id = etStudentId.text.toString().trim()

        if (name.isEmpty() || id.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show()
            return
        }

        val student = Student(id, name)
        studentList.add(student)
        adapter.notifyItemInserted(studentList.size - 1)
        clearInputs()
        Toast.makeText(this, "Đã thêm sinh viên", Toast.LENGTH_SHORT).show()
    }

    private fun updateStudent() {
        if (selectedPosition == -1) {
            Toast.makeText(this, "Vui lòng chọn sinh viên cần cập nhật", Toast.LENGTH_SHORT).show()
            return
        }

        val name = etName.text.toString().trim()
        val id = etStudentId.text.toString().trim()

        if (name.isEmpty() || id.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show()
            return
        }

        studentList[selectedPosition] = Student(id, name)
        adapter.notifyItemChanged(selectedPosition)
        clearInputs()
        selectedPosition = -1
        Toast.makeText(this, "Đã cập nhật sinh viên", Toast.LENGTH_SHORT).show()
    }

    private fun deleteStudent(position: Int) {
        studentList.removeAt(position)
        adapter.notifyItemRemoved(position)
        if (selectedPosition == position) {
            clearInputs()
            selectedPosition = -1
        } else if (selectedPosition > position) {
            selectedPosition--
        }
        Toast.makeText(this, "Đã xóa sinh viên", Toast.LENGTH_SHORT).show()
    }

    private fun selectStudent(position: Int) {
        selectedPosition = position
        val student = studentList[position]
        etStudentId.setText(student.studentId)
        etName.setText(student.name)
    }

    private fun clearInputs() {
        etStudentId.text?.clear()
        etName.text?.clear()
        selectedPosition = -1
    }

    private fun loadSampleData() {
        studentList.addAll(
            listOf(
                Student("20200001", "Nguyễn Văn A"),
                Student("20200002", "Trần Thị B"),
                Student("20200003", "Lê Văn C"),
                Student("20200004", "Phạm Thị D"),
                Student("20200005", "Hoàng Văn E"),
                Student("20200006", "Vũ Thị F"),
                Student("20200007", "Đặng Văn G"),
                Student("20200008", "Bùi Thị H"),
                Student("20200009", "Hồ Văn I")
            )
        )
        adapter.notifyDataSetChanged()
    }
}