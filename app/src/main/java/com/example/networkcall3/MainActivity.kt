package com.example.networkcall3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.networkcall3.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import retrofit2.Response
import retrofit2.http.GET

interface ApiService
{
    @GET("api/activity")
    suspend fun listRepos(): Response<RepoData>
}
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        observeRepose()
        binding.button.setOnClickListener {
            viewModel.retrieveRepos()
        }
        viewModel.retrieveRepos()
        val view = binding.root
        setContentView(view)

    }
    private fun observeRepose(){
        viewModel.repoList.observe(this){
            //set repository and correct dat
            setData(it)
        }
        viewModel.errorList.observe(this){
            // handle the error
            Snackbar.make(
                findViewById(R.id.main_view),
                "error retrieving repos",
                Snackbar.LENGTH_INDEFINITE
            ).setAction("Retry"){
                viewModel.retrieveRepos()
            }.show()

        }
    }
    private fun setData(repoData: RepoData){
        binding.thirdTextview.text = repoData.activity
        binding.fifthTextview.text = getString(R.string.tv_price, repoData.price.toString())
        binding.seventhTextview.text = repoData.participants.toString()
    }
}