package com.example.submissionawal.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submissionawal.api.RetrofitClient
import com.example.submissionawal.data.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class FollowingViewModel : ViewModel() {
val listFollowing = MutableLiveData<ArrayList<User>>()

    fun setListFollowing(username :String){
        RetrofitClient.apiInstance
            .getFollowers(username)
            .enqueue(object :Callback<ArrayList<User>>{
                override fun onResponse(
                    call: Call<ArrayList<User>>,
                    response: Response<ArrayList<User>>
                ) {
                    if (response.isSuccessful){
                        listFollowing.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                    t.message?.let { Log.e("Failure", it) }
                }

            })
    }
    fun getListFollowers():LiveData<ArrayList<User>>{
        return listFollowing
    }
}