package com.example.retrofit2

import com.example.adapter.CardUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

class Retrofit {

    private val userList = mutableListOf<CardUser>()
    private val api: UserApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UserApi::class.java)
    }

    interface UserApi {
        @GET("/users")
        fun getUsers(): Call<List<User>>

        @GET("/users/{id}")
        fun getUserById(@Path("id") id: Int): Call<User>
    }

    fun getUserById(id: Int, callback: (User) -> Unit) {
        api.getUserById(id).enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    val user = response.body()
                    if (user != null) {
                        callback(user)
                    } else {
                        // Обработка ошибки
                    }
                } else {
                    // Обработка ошибки
                }
            }
            override fun onFailure(call: Call<User>, t: Throwable) {

            }
        })
    }
    fun getUsersList(callback: (List<CardUser>) -> Unit) {
        api.getUsers().enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (response.isSuccessful) {
                    val users = response.body()
                    val totalUsers = users?.size ?: 0
                    var completedUsers = 0
                    for (i in 1..totalUsers) {
                        api.getUserById(i).enqueue(object : Callback<User> {
                            override fun onResponse(call: Call<User>, response: Response<User>) {
                                if (response.isSuccessful) {
                                    val user = response.body()
                                    userList.add(CardUser(R.drawable.enter1, user!!.name, user.phone, user.id))
                                } else {
                                    // Обработка ошибки
                                }
                                completedUsers++
                                if (completedUsers == totalUsers) {
                                    callback(userList)
                                }
                            }

                            override fun onFailure(call: Call<User>, t: Throwable) {
                                completedUsers++
                                if (completedUsers == totalUsers) {
                                    callback(userList)
                                }
                            }
                        })
                    }
                } else {
                    callback(emptyList())
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                callback(emptyList())
            }
        })
    }

}