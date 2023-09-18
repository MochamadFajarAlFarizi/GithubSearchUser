package com.example.submissionawal.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submissionawal.R
import com.example.submissionawal.data.model.User
import com.example.submissionawal.databinding.ActivityMainBinding
import com.example.submissionawal.ui.detail.DetailUserActivity

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: UserAdapter
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter= UserAdapter()
        adapter.notifyDataSetChanged()

        adapter.setOnItemClickCallback(object :UserAdapter.OnItemClickCallback{
            override fun onItemCliked(data: User) {
                Intent(this@MainActivity,DetailUserActivity::class.java).also {
                    it.putExtra(DetailUserActivity.EXTRA_USERNAME,data.login)
                    startActivity(it)
                }
            }

        })
        viewModel= ViewModelProvider(this,ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)

        binding.apply {
            rvUser.layoutManager = LinearLayoutManager(this@MainActivity)
            rvUser.setHasFixedSize(true)
            rvUser.adapter=adapter

            btnSearch.setOnClickListener{
                searchUser()
            }
            etQuery.setOnKeyListener{v , keyCode, event->
                if (event.action == KeyEvent.ACTION_DOWN && keyCode== KeyEvent.KEYCODE_ENTER){
                    searchUser()
                    return@setOnKeyListener true
                }
                return@setOnKeyListener false
            }
        }

        viewModel.getSearchUsers().observe(this,{
            if (it!=null){
                adapter.setList(it)
                showLoading(false)
            }
        })
    }
    private fun searchUser(){
        binding.apply {
            val query =etQuery.text.toString()
            if (query.isEmpty())return
            showLoading(true)
            viewModel.setSearchUsers(query)
        }
    }

    private fun showLoading(state:Boolean){
        if (state){
            binding.progressBar.visibility= View.VISIBLE
        }else {
            binding.progressBar.visibility= View.GONE
        }
    }
}