package com.capstone.schero.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.capstone.schero.data.response.ResponseScholarshipItem
import com.capstone.schero.databinding.ItemScholarshipBinding
import com.capstone.schero.ui.detail.DetailActivity
import java.util.ArrayList

class RecommendationAdapter : RecyclerView.Adapter<RecommendationAdapter.FavoriteViewHolder>() {

    private val list = ArrayList<ResponseScholarshipItem>()

    fun submitData(list: List<ResponseScholarshipItem>) {
        val diffCallback = UserDiffCallback(this.list, list)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.list.clear()
        this.list.addAll(list)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding = ItemScholarshipBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding)
    }
    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(list[position])
    }
    override fun getItemCount(): Int {
        return list.size
    }

    inner class FavoriteViewHolder(private val binding: ItemScholarshipBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(scholarship: ResponseScholarshipItem) {
            with(binding) {
                scholarshipNameTextView.text = scholarship.namaBeasiswa
                prodiTextView.text = scholarship.prodi
                locationTextView.text = scholarship.negara
                if (scholarship.iPK == "0") {
                    ipkTextView.visibility = View.VISIBLE
                } else {
                    ipkTextView.text = scholarship.iPK
                }
                jenjangTextView.text = scholarship.jenjang
                itemCardView.setOnClickListener {
                    val intent = Intent(it.context, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.EXTRA_NAME, scholarship.namaBeasiswa)
                    it.context.startActivity(intent)
                }
            }
        }
    }

    class UserDiffCallback(
        private val oldList: List<ResponseScholarshipItem>,
        private val newList: List<ResponseScholarshipItem>
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].iD == newList[newItemPosition].iD
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldBeasiswa = oldList[oldItemPosition]
            val newBeasiswa = newList[newItemPosition]
            return oldBeasiswa.iD == newBeasiswa.iD
        }
    }
}