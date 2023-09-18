package com.example.submissionawal.ui.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submissionawal.R
import com.example.submissionawal.databinding.FragmentFollowBinding
import com.example.submissionawal.ui.main.UserAdapter

class FollowingFragment:Fragment(R.layout.fragment_follow) {

    private var _binding : FragmentFollowBinding?=null
    private val binding get() = _binding!!
    private lateinit var viewModel: FollowingViewModel
    private lateinit var adapter: UserAdapter
    private lateinit var username:String


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args =arguments
        username = args?.getString(DetailUserActivity.EXTRA_USERNAME).toString()
        _binding = FragmentFollowBinding.bind(view)

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        binding.apply {
            rvUser.setHasFixedSize(true)
            rvUser.layoutManager= LinearLayoutManager(activity)
            rvUser.adapter=adapter
        }
        showLoading(true)
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(FollowingViewModel::class.java)
        viewModel.setListFollowing(username)
        viewModel.getListFollowers().observe(viewLifecycleOwner,{
            if (it!=null){
                adapter.setList(it)
                showLoading(false)
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
    private fun showLoading(state:Boolean){
        if (state){
            binding.progressBar.visibility= View.VISIBLE
        }else {
            binding.progressBar.visibility= View.GONE
        }
    }
}
