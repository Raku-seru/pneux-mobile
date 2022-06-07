package com.c22ps208.pneux.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.c22ps208.pneux.data.remote.response.NewsResponse
import com.c22ps208.pneux.databinding.ItemListNewsBinding

class NewsAdapter : RecyclerView.Adapter<MyViewHolder>() {
    private var listNewsResponse = NewsResponse()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = ItemListNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(listNewsResponse[position])
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClickedResp(listNewsResponse[position]) }
    }

    override fun getItemCount() = listNewsResponse.size
}