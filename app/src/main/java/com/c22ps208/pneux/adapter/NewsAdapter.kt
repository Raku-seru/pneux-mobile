package com.c22ps208.pneux.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.c22ps208.pneux.data.remote.response.NewsResponse
import com.c22ps208.pneux.databinding.ItemListNewsBinding
import com.c22ps208.pneux.adapter.NewsAdapter.MyViewHolder
import com.c22ps208.pneux.data.remote.response.ArticlesItem

class NewsAdapter : RecyclerView.Adapter<MyViewHolder>() {
    private var articlesItem = ArrayList<ArticlesItem>()

    @SuppressLint("NotifyDataSetChanged")
    fun addDataToList(items: List<ArticlesItem>) {
        articlesItem.clear()
        val itr = items.iterator()
        while (itr.hasNext()) {
            articlesItem.add(itr.next())
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = ItemListNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(articlesItem[position])
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(articlesItem[position]) }
    }

    override fun getItemCount() = articlesItem.size

    class MyViewHolder(private var binding: ItemListNewsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(articlesItem: ArticlesItem) {
            binding.tvTitleNews.text = articlesItem.title
            binding.tvDescNews.text = articlesItem.description
        }
    }

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }
}