package com.example.a18112025

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.a18112025.databinding.FragmentAddStudentBinding

class AddStudentFragment : Fragment() {
    private lateinit var binding: FragmentAddStudentBinding
    private val viewModel: StudentViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentAddStudentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnCancel.setOnClickListener {
            findNavController().popBackStack() // Quay lại màn hình trước
        }

        binding.btnSave.setOnClickListener {
            val student = Student(
                binding.etId.text.toString(),
                binding.etName.text.toString(),
                binding.etPhone.text.toString(),
                binding.etAddress.text.toString()
            )

            if(student.studentId.isNotEmpty() && student.name.isNotEmpty()) {
                viewModel.addStudent(student)
                Toast.makeText(context, "Thêm thành công", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack() // Quay lại màn hình trước
            } else {
                Toast.makeText(context, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show()
            }
        }
    }
}