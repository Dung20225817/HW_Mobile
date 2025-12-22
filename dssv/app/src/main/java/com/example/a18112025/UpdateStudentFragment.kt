package com.example.a18112025

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.a18112025.databinding.FragmentUpdateStudentBinding

class UpdateStudentFragment : Fragment() {
    private lateinit var binding: FragmentUpdateStudentBinding
    private val viewModel: StudentViewModel by activityViewModels()
    private var position: Int = -1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentUpdateStudentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Nhận dữ liệu
        val studentArg = arguments?.getSerializable("student") as? Student
        position = arguments?.getInt("position", -1) ?: -1

        // --- THAY ĐỔI: Gán dữ liệu thủ công (View Binding) ---
        if (studentArg != null) {
            // Không dùng binding.student = ... nữa
            binding.etId.setText(studentArg.studentId)
            binding.etName.setText(studentArg.name)
            binding.etPhone.setText(studentArg.phoneNumber)
            binding.etAddress.setText(studentArg.address)
        }
        // -----------------------------------------------------

        binding.btnCancel.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnUpdate.setOnClickListener {
            val updatedStudent = Student(
                binding.etId.text.toString(),
                binding.etName.text.toString(),
                binding.etPhone.text.toString(),
                binding.etAddress.text.toString()
            )

            viewModel.updateStudent(position, updatedStudent)
            Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
        }
    }
}