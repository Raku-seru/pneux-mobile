package com.c22ps208.pneux.adapter

import com.c22ps208.pneux.data.remote.response.ArticlesItem

interface OnItemClickCallback {
    fun onItemClicked(articlesItem: ArticlesItem)
}