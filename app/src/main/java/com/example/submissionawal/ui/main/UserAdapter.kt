package com.example.submissionawal.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.submissionawal.data.model.User
import com.example.submissionawal.databinding.ItemUserBinding

class UserAdapter:RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private val list =ArrayList<User>()

    private var onItemClickCallback:OnItemClickCallback?=null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback=onItemClickCallback
    }
    fun setList(user: ArrayList<User>){
        list.clear()
        list.addAll(user)
        notifyDataSetChanged()
    }
    inner class UserViewHolder(val binding: ItemUserBinding) :RecyclerView.ViewHolder(binding.root){
        fun bind(user: User){

            binding.root.setOnClickListener{
                onItemClickCallback?.onItemCliked(user)
            }
            binding.apply {
                Glide.with(itemView)
                    .load(user.avatar_url)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .centerCrop()
                    .into(ivUser)
                tvUsername.text=user.login
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view=ItemUserBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return UserViewHolder((view))
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

interface OnItemClickCallback{
    fun onItemCliked(data:User)
}
}