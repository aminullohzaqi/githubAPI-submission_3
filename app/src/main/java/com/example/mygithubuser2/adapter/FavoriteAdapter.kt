package com.example.mygithubuser2.adapter

import android.app.Application
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mygithubuser2.database.Favorite
import com.example.mygithubuser2.databinding.ItemFavoriteBinding
import com.example.mygithubuser2.helper.FavoriteDiffCallback
import com.example.mygithubuser2.ui.DetailActivity
import com.example.mygithubuser2.viewmodel.FavoriteViewModel

class FavoriteAdapter: RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    private lateinit var favoriteViewModel: FavoriteViewModel
    private val listFavorites = ArrayList<Favorite>()
    private var favorites: Favorite? = null

    fun setListFavorites(listFavorites: List<Favorite>){
        val diffCallback = FavoriteDiffCallback(this.listFavorites, listFavorites)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listFavorites.clear()
        this.listFavorites.addAll(listFavorites)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding = ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(listFavorites[position])
    }

    override fun getItemCount(): Int {
        return listFavorites.size
    }

    inner class FavoriteViewHolder(private val binding: ItemFavoriteBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(favorite: Favorite){
            with(binding){
                tvItemTitle.text = favorite.username
                tvItemUrl.text = favorite.url
                Glide.with(itemView.context)
                    .load(favorite.imageProfile)
                    .into(itemImg)

                itemView.setOnClickListener{
                    val detailActivity = Intent(itemView.context, DetailActivity::class.java)
                    detailActivity.putExtra(DetailActivity.EXTRA_USER, "${favorite.username}")
                    detailActivity.putExtra(DetailActivity.EXTRA_URL, "${favorite.url}")
                    detailActivity.putExtra(DetailActivity.EXTRA_IMG,"${favorite.imageProfile}")
                    itemView.context.startActivity(detailActivity)
                }

                clearBtn.setOnClickListener{
                    favoriteViewModel = FavoriteViewModel(Application())
                    favorites = Favorite(favorite.username)
                    favorites.let { favorites ->
                        favorites?.username = favorite.username }
                    favoriteViewModel.delete(favorites as Favorite)
                }
            }
        }
    }

}