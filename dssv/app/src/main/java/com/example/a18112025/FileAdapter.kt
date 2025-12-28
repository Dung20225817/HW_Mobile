package com.example.a18112025

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.io.File

class FileAdapter(
    private var files: List<File>,
    private val onItemClick: (File) -> Unit,
    private val onItemLongClick: (File, View) -> Unit
) : RecyclerView.Adapter<FileAdapter.FileViewHolder>() {

    inner class FileViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tvName)
        val tvDetails: TextView = itemView.findViewById(R.id.tvDetails)
        val ivIcon: ImageView = itemView.findViewById(R.id.ivIcon)

        fun bind(file: File) {
            tvName.text = file.name
            if (file.isDirectory) {
                ivIcon.setImageResource(android.R.drawable.ic_menu_more) // Icon folder
                tvDetails.text = "Thư mục"
            } else {
                ivIcon.setImageResource(android.R.drawable.ic_menu_agenda) // Icon file
                tvDetails.text = "${file.length() / 1024} KB"
            }

            itemView.setOnClickListener { onItemClick(file) }
            itemView.setOnLongClickListener {
                onItemLongClick(file, itemView) // Truyền View để neo ContextMenu
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_file, parent, false)
        return FileViewHolder(view)
    }

    override fun onBindViewHolder(holder: FileViewHolder, position: Int) {
        holder.bind(files[position])
    }

    override fun getItemCount() = files.size

    fun updateData(newFiles: List<File>) {
        files = newFiles
        notifyDataSetChanged()
    }
}