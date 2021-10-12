package com.example.mygithubuser2.ui

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.mygithubuser2.viewmodel.FavoriteViewModel
import com.example.mygithubuser2.viewmodel.MainViewModel
import com.example.mygithubuser2.R
import com.example.mygithubuser2.adapter.SectionsPagerAdapter
import com.example.mygithubuser2.database.Favorite
import com.example.mygithubuser2.databinding.ActivityDetailBinding
import com.example.mygithubuser2.helper.ViewModelFactory
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var favoriteViewModel: FavoriteViewModel

    companion object{
        const val EXTRA_USER = "extra user"
        const val EXTRA_URL = "extra url"
        const val EXTRA_IMG = "extra img"

        private val TAB_TITLES = intArrayOf(
            R.string.follower_label,
            R.string.following_label
        )

        private var favorite: Favorite? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = null
        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#0F0F0F")))

        val username = intent.getStringExtra(EXTRA_USER)

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        sectionsPagerAdapter.username = username
        binding.viewPager.adapter = sectionsPagerAdapter
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f

        mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            MainViewModel::class.java)

        if (username != null) {
            showLoading(true)
            mainViewModel.getDetailUser(username)
        }

        mainViewModel.detailUser.observe(this, { detailUser ->
            binding.detailUsername.text = detailUser.username
            binding.detailName.text = detailUser.name
            binding.detailRepository.text = detailUser.public_repos.toString()
            binding.detailFollower.text = detailUser.followers.toString()
            binding.detailFollowing.text = detailUser.following.toString()
            Glide.with(this@DetailActivity.applicationContext)
                .load(detailUser.imageProfile)
                .into(binding.detailImg)

            showLoading(false)
        })
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavoriteViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(FavoriteViewModel::class.java)
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
            binding.linearLayout.visibility = View.INVISIBLE
            binding.linearLayout2.visibility = View.INVISIBLE
            binding.tabs.visibility = View.INVISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
            binding.linearLayout.visibility = View.VISIBLE
            binding.linearLayout2.visibility = View.VISIBLE
            binding.tabs.visibility = View.VISIBLE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.detail_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.favorite -> {
                val username = intent.getStringExtra(EXTRA_USER)
                val url = intent.getStringExtra(EXTRA_URL)
                val img = intent.getStringExtra(EXTRA_IMG)

                favoriteViewModel = obtainViewModel(this@DetailActivity)
                favorite = username?.let { Favorite(it) }

                favorite.let { favorite ->
                    if (username != null) {
                        favorite?.username = username
                    }
                    favorite?.url = url
                    favorite?.imageProfile = img
                }
                favoriteViewModel.insert(favorite as Favorite)
                Toast.makeText(this, getString(R.string.added_success), Toast.LENGTH_SHORT).show()
                return true
            }

            R.id.share -> {
                val url = intent.getStringExtra(EXTRA_URL)
                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, "$url")
                    type = "text/plain"
                }

                val shareIntent = Intent.createChooser(sendIntent, null)
                startActivity(shareIntent)
                return true
            }

            android.R.id.home ->{
                finish()
                return true
            }

            else -> return true
        }
    }
}