package com.example.a18112025

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StudentAdapter(
    private val students: List<Student>,
    private val onDeleteClick: (Int) -> Unit,
    private val onItemClick: (Int) -> Unit // Trả về vị trí khi click vào item
) : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

    inner class StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvName: TextView = itemView.findViewById(R.id.tvName)
        private val tvStudentId: TextView = itemView.findViewById(R.id.tvStudentId)
        private val btnDelete: ImageButton = itemView.findViewById(R.id.btnDelete)

        fun bind(student: Student, position: Int) {
            tvName.text = student.name
            tvStudentId.text = student.studentId

            // Nút xóa giữ nguyên logic cũ
            btnDelete.setOnClickListener {
                onDeleteClick(position)
            }

            // Click vào item để mở chi tiết
            itemView.setOnClickListener {
                onItemClick(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_student, parent, false)
        return StudentViewHolder(view)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        holder.bind(students[position], position)
    }

    override fun getItemCount() = students.size
}