package com.example.networkcall3

import androidx.core.R
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class MainViewModel: ViewModel() {
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://www.boredapi.com/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService = retrofit.create(ApiService::class.java)

    private val repos = MutableLiveData<RepoData>()
    val repoList: LiveData<RepoData>
    get() = repos

    private var error = MutableLiveData<String>()
    val errorList: LiveData<String>
    get() = error

    fun retrieveRepos(){
        CoroutineScope(Dispatchers.Main).launch {
            try {
                repos.value = apiService.listRepos().body()
            }catch (e:Exception)
            {
                error.value = e.localizedMessage
            }
        }
    }

}