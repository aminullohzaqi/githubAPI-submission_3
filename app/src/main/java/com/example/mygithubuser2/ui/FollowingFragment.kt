package com.example.mygithubuser2.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mygithubuser2.adapter.ListUserAdapter
import com.example.mygithubuser2.viewmodel.MainViewModel
import com.example.mygithubuser2.User
import com.example.mygithubuser2.databinding.FragmentFollowingBinding

class FollowingFragment : Fragment() {

    private lateinit var binding: FragmentFollowingBinding
    private lateinit var adapter: ListUserAdapter
    private lateinit var mainViewModel: MainViewModel
    private var list: ArrayList<User> = arrayListOf()

    companion object {
        private const val EXTRA_USERNAME = "username"

        fun newInstance(username: String): FollowingFragment {
            val fragment = FollowingFragment()
            val bundle = Bundle()
            bundle.putString(EXTRA_USERNAME, username)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFollowingBinding.inflate(inflater, container, false)
        return binding.root
    }

    @Suppress("DEPRECATION")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (arguments != null) {
            val username = arguments?.getString(EXTRA_USERNAME)

            adapter = ListUserAdapter(list)
            adapter.notifyDataSetChanged()

            binding.recyclerviewFollowing.layoutManager = LinearLayoutManager(activity)
            binding.recyclerviewFollowing.adapter = adapter

            mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
                MainViewModel::class.java)

            if (username != null) {
                showLoading(true)
                mainViewModel.setFollowing(username)
            }

            mainViewModel.listFollowing.observe(viewLifecycleOwner, { listFollowing ->
                if (listFollowing != null) {
                    adapter.setData(listFollowing as ArrayList<User>)
                    showLoading(false)
                } else{
                    binding.notFound.visibility = View.VISIBLE
                    showLoading(false)
                }
            })
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}