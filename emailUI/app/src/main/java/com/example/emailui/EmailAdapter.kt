package com.example.emailui

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EmailAdapter(
    private val emails: List<Email>,
    private val onItemClick: (Int) -> Unit
) : RecyclerView.Adapter<EmailAdapter.EmailViewHolder>() {

    inner class EmailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val avatarView: TextView = itemView.findViewById(R.id.tvAvatar)
        private val senderView: TextView = itemView.findViewById(R.id.tvSender)
        private val subjectView: TextView = itemView.findViewById(R.id.tvSubject)
        private val previewView: TextView = itemView.findViewById(R.id.tvPreview)
        private val timeView: TextView = itemView.findViewById(R.id.tvTime)
        private val starView: ImageView = itemView.findViewById(R.id.ivStar)

        fun bind(email: Email, position: Int) {
            // Set avatar
            avatarView.text = email.avatar
            val drawable = avatarView.background as GradientDrawable
            drawable.setColor(Color.parseColor(email.avatarColor))

            // Set text content
            senderView.text = email.sender
            subjectView.text = email.subject
            previewView.text = email.preview
            timeView.text = email.time

            // Set star icon
            if (email.isStarred) {
                starView.setImageResource(android.R.drawable.star_on)
            } else {
                starView.setImageResource(android.R.drawable.star_off)
            }

            itemView.setOnClickListener {
                onItemClick(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmailViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_email, parent, false)
        return EmailViewHolder(view)
    }

    override fun onBindViewHolder(holder: EmailViewHolder, position: Int) {
        holder.bind(emails[position], position)
    }

    override fun getItemCount() = emails.size
}