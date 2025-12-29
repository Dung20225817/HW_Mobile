package com.example.a18112025

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a18112025.databinding.FragmentStudentListBinding

class StudentListFragment : Fragment() {
    private lateinit var binding: FragmentStudentListBinding
    // Sử dụng activityViewModels để chia sẻ dữ liệu với Activity và các Fragment khác
    private val viewModel: StudentViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStudentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        val adapter = StudentAdapter(listOf(),
            onDeleteClick = { position -> viewModel.deleteStudent(position) },
            onItemClick = { position ->
                // Chuyển sang màn hình update, gửi kèm vị trí và đối tượng
                val student = viewModel.studentList.value?.get(position)
                val bundle = Bundle().apply {
                    putSerializable("student", student)
                    putInt("position", position)
                }
                findNavController().navigate(R.id.action_list_to_update, bundle)
            }
        )

        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = adapter

        // Quan sát dữ liệu từ ViewModel
        viewModel.studentList.observe(viewLifecycleOwner) { list ->
            // Cập nhật adapter khi dữ liệu thay đổi (Cách đơn giản: tạo adapter mới hoặc update list trong adapter)
            // Để đơn giản cho bài tập, ta set lại data cho adapter (cần sửa adapter thêm hàm setData hoặc tạo mới)
            val newAdapter = StudentAdapter(list,
                onDeleteClick = { pos -> viewModel.deleteStudent(pos) },
                onItemClick = { pos ->
                    val student = list[pos]
                    val bundle = Bundle().apply {
                        putSerializable("student", student)
                        putInt("position", pos)
                    }
                    findNavController().navigate(R.id.action_list_to_update, bundle)
                }
            )
            binding.recyclerView.adapter = newAdapter
        }

        binding.fabAdd.setOnClickListener {
            findNavController().navigate(R.id.action_list_to_add)
        }
    }
}