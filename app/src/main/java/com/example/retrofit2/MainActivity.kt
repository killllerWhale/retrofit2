package com.example.retrofit2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.CardEmployee
import com.example.retrofit2.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Path

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val userList = mutableListOf<CardEmployee>()
    private val adapter by lazy { CardAdapterFavourites() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        CoroutineScope(Dispatchers.IO).launch {
            val users = api.getUsers()
            users.forEachIndexed { index, userMap ->
                val userId = (userMap["id"] as Double).toInt()
                val user = api.getUserById(userId)
                val name = user["name"] as String
                val phone = user["phone"] as String
                val userObj = CardEmployee(R.drawable.enter1 ,name, phone)
                userList.add(userObj)

                if (index == users.size - 1) {
                    runOnUiThread {
                        binding.recyclerFavourites.run {
                            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                            this.adapter = this@MainActivity.adapter
                        }

                        adapter.submitList(userList)
                    }
                }
            }
        }
    }

    private val api: MyApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MyApi::class.java)
    }

        interface MyApi{
            @GET("/users")
            suspend fun getUsers(): List<Map<String,Any>>

            @GET("/users/{id}")
            suspend fun getUserById(@Path("id") id: Int): Map<String,Any>
        }

}