package com.djevannn.capstoneproject.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.djevannn.capstoneproject.R
import com.djevannn.capstoneproject.data.source.remote.response.BookResponse
import com.djevannn.capstoneproject.databinding.ItemBookListBinding
import java.util.*

class HomeAdapter :
    RecyclerView.Adapter<HomeAdapter.ListViewHolder>() {

    private var listData = ArrayList<BookResponse>()
    var onItemClick: ((BookResponse) -> Unit)? = null

    fun setData(newListData: List<BookResponse>?) {
        if (newListData == null) return
        listData.clear()
        listData.addAll(newListData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HomeAdapter.ListViewHolder =
        ListViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.item_book_list,
                    parent,
                    false
                )
        )

    override fun onBindViewHolder(
        holder: HomeAdapter.ListViewHolder,
        position: Int
    ) {
        val data = listData[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int = listData.size

    inner class ListViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        private val binding = ItemBookListBinding.bind(itemView)
        fun bind(data: BookResponse) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(data.image)
                    .into(ivItemPoster)
                tvItemTitle.text = data.title
                tvItemAuthor.text = data.author
            }
        }

        init {
            binding.btnItemPlay.setOnClickListener {
                onItemClick?.invoke(listData[adapterPosition])
            }
        }
    }

}