package com.example.mygithubuser2.helper

import androidx.recyclerview.widget.DiffUtil
import com.example.mygithubuser2.database.Favorite

class FavoriteDiffCallback(private val mOldFavoriteList: List<Favorite>, private val mNewFavoriteList: List<Favorite>): DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return mOldFavoriteList.size
    }

    override fun getNewListSize(): Int {
        return mNewFavoriteList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldFavoriteList[oldItemPosition].username== mNewFavoriteList[newItemPosition].username
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldFavorite = mOldFavoriteList[oldItemPosition]
        val newFavorite = mNewFavoriteList[newItemPosition]
        return oldFavorite.username == newFavorite.username && newFavorite.url == newFavorite.url

    }

}