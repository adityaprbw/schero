package com.capstone.schero.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.schero.data.response.BookmarksScholarship
import com.capstone.schero.databinding.ItemScholarshipBinding
import com.capstone.schero.ui.detail.DetailActivity
import java.util.ArrayList

class BookmarksAdapter : RecyclerView.Adapter<BookmarksAdapter.BookmarkViewHolder>() {

    private val list = ArrayList<BookmarksScholarship>()

    fun submitData(scholarships: List<BookmarksScholarship>) {
        val diffCallback = UserDiffCallback(this.list, scholarships)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.list.clear()
        this.list.addAll(scholarships)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarkViewHolder {
        val binding = ItemScholarshipBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookmarkViewHolder(binding)
    }
    override fun onBindViewHolder(holder: BookmarkViewHolder, position: Int) {
        holder.bind(list[position])
    }
    override fun getItemCount(): Int {
        return list.size
    }

    inner class BookmarkViewHolder(private val binding: ItemScholarshipBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(scholarship: BookmarksScholarship) {
            with(binding) {
                Glide.with(itemView)
                    .load(scholarship.linkGambar)
                    .into(binding.scholarshipImageView)
                scholarshipNameTextView.text = scholarship.namaBeasiswa

                locationTextView.text = scholarship.negara

                if (scholarship.iPK == "0.0") ipkTextView.visibility = View.VISIBLE
                else ipkTextView.text = scholarship.iPK

                itemCardView.setOnClickListener {
                    val intent = Intent(it.context, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.EXTRA_NAME, scholarship.namaBeasiswa)
                    it.context.startActivity(intent)
                }
            }
        }
    }

    class UserDiffCallback(
        private val oldList: List<BookmarksScholarship>,
        private val newList: List<BookmarksScholarship>
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].namaBeasiswa == newList[newItemPosition].namaBeasiswa
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldList = oldList[oldItemPosition]
            val newList = newList[newItemPosition]
            return oldList.namaBeasiswa == newList.namaBeasiswa
        }
    }
}
