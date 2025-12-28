package com.example.a18112025

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.view.*
import android.widget.EditText
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.activity.addCallback
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.a18112025.databinding.FragmentFileExplorerBinding
import java.io.File

class FileExplorerFragment : Fragment() {
    private lateinit var binding: FragmentFileExplorerBinding
    private lateinit var adapter: FileAdapter
    private var currentPath: File = Environment.getExternalStorageDirectory() // Mặc định là sdcard
    private var selectedFile: File? = null // File đang được chọn để thao tác context menu

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentFileExplorerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupMenu()
        checkPermissionAndLoadFiles()

        // Xử lý nút Back cứng của điện thoại để quay lại thư mục cha
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            if (currentPath.absolutePath == Environment.getExternalStorageDirectory().absolutePath) {
                isEnabled = false // Nếu đang ở gốc thì thoát app (hoặc quay lại màn hình trước)
                requireActivity().onBackPressed()
            } else {
                currentPath = currentPath.parentFile ?: currentPath
                loadFiles(currentPath)
            }
        }
    }

    private fun setupMenu() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_file_explorer, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.menu_create_folder -> {
                        showCreateDialog(isFolder = true)
                        true
                    }
                    R.id.menu_create_file -> {
                        showCreateDialog(isFolder = false)
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun checkPermissionAndLoadFiles() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (!Environment.isExternalStorageManager()) {
                val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                intent.data = Uri.parse("package:" + requireContext().packageName)
                startActivity(intent)
            } else {
                loadFiles(currentPath)
            }
        } else {
            // Với Android cũ hơn, cần request permission runtime (bỏ qua để code ngắn gọn vì đề bài thường dùng máy mới)
            loadFiles(currentPath)
        }
    }

    private fun loadFiles(directory: File) {
        currentPath = directory
        binding.tvPath.text = currentPath.absolutePath
        val files = directory.listFiles()?.toList() ?: emptyList()

        adapter = FileAdapter(files,
            onItemClick = { file ->
                if (file.isDirectory) {
                    loadFiles(file) // Mở thư mục
                } else {
                    openFile(file) // Xem file
                }
            },
            onItemLongClick = { file, anchorView ->
                showContextMenu(file, anchorView)
            }
        )
        binding.rvFiles.layoutManager = LinearLayoutManager(context)
        binding.rvFiles.adapter = adapter
    }

    // --- XỬ LÝ CONTEXT MENU ---
    private fun showContextMenu(file: File, view: View) {
        val popup = PopupMenu(requireContext(), view)
        popup.menu.add("Đổi tên")
        popup.menu.add("Xóa")
        if (!file.isDirectory) popup.menu.add("Sao chép đến...")

        popup.setOnMenuItemClickListener { item ->
            when (item.title) {
                "Đổi tên" -> showRenameDialog(file)
                "Xóa" -> showDeleteDialog(file)
                "Sao chép đến..." -> showCopyDialog(file)
            }
            true
        }
        popup.show()
    }

    // --- CÁC HÀM CHỨC NĂNG ---

    private fun openFile(file: File) {
        val name = file.name.lowercase()
        if (name.endsWith(".txt")) {
            val content = file.readText()
            AlertDialog.Builder(context)
                .setTitle(file.name)
                .setMessage(content)
                .setPositiveButton("Đóng", null)
                .show()
        } else if (name.endsWith(".png") || name.endsWith(".jpg") || name.endsWith(".bmp")) {
            // Hiển thị ảnh trong Dialog
            val imageView = android.widget.ImageView(context)
            Glide.with(this).load(file).into(imageView)
            AlertDialog.Builder(context)
                .setTitle(file.name)
                .setView(imageView)
                .setPositiveButton("Đóng", null)
                .show()
        } else {
            Toast.makeText(context, "Không hỗ trợ mở file này", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showCreateDialog(isFolder: Boolean) {
        val input = EditText(context)
        input.hint = if (isFolder) "Tên thư mục" else "Tên file (vd: note.txt)"
        AlertDialog.Builder(context)
            .setTitle(if (isFolder) "Tạo thư mục mới" else "Tạo file mới")
            .setView(input)
            .setPositiveButton("Tạo") { _, _ ->
                val name = input.text.toString()
                if (name.isNotEmpty()) {
                    val newFile = File(currentPath, name)
                    if (isFolder) newFile.mkdir() else newFile.createNewFile()
                    loadFiles(currentPath)
                }
            }
            .setNegativeButton("Hủy", null)
            .show()
    }

    private fun showRenameDialog(file: File) {
        val input = EditText(context)
        input.setText(file.name)
        AlertDialog.Builder(context)
            .setTitle("Đổi tên")
            .setView(input)
            .setPositiveButton("Lưu") { _, _ ->
                val newName = input.text.toString()
                val newFile = File(currentPath, newName)
                if (file.renameTo(newFile)) loadFiles(currentPath)
                else Toast.makeText(context, "Lỗi đổi tên", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Hủy", null)
            .show()
    }

    private fun showDeleteDialog(file: File) {
        AlertDialog.Builder(context)
            .setTitle("Xác nhận xóa")
            .setMessage("Bạn có chắc muốn xóa ${file.name}?")
            .setPositiveButton("Xóa") { _, _ ->
                if (file.isDirectory) file.deleteRecursively() else file.delete()
                loadFiles(currentPath)
                Toast.makeText(context, "Đã xóa", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Hủy", null)
            .show()
    }

    private fun showCopyDialog(file: File) {
        // Logic copy đơn giản: Copy thành bản sao ngay tại thư mục hiện tại (hoặc bạn có thể nhập đường dẫn)
        val input = EditText(context)
        input.hint = "Nhập tên mới hoặc đường dẫn đích"
        input.setText("Copy_${file.name}")

        AlertDialog.Builder(context)
            .setTitle("Sao chép file")
            .setMessage("Nhập tên cho bản sao:")
            .setView(input)
            .setPositiveButton("Sao chép") { _, _ ->
                val newName = input.text.toString()
                val destFile = File(currentPath, newName)
                try {
                    file.copyTo(destFile, overwrite = true)
                    loadFiles(currentPath)
                    Toast.makeText(context, "Đã sao chép", Toast.LENGTH_SHORT).show()
                } catch (e: Exception) {
                    Toast.makeText(context, "Lỗi sao chép: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Hủy", null)
            .show()
    }
}