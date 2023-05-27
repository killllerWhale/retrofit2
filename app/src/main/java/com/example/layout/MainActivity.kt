package com.example.layout
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adapter.CardAdapterUser
import com.example.retrofit2.R
import com.example.retrofit2.Retrofit
import com.example.retrofit2.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = CardAdapterUser { clickedItem ->
            loadFragment(UserFragment(), clickedItem.id)
            Toast.makeText(this, "Нажата карточка: ${clickedItem.name}", Toast.LENGTH_SHORT).show()
        }

        binding.recyclerFavourites.run {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            this.adapter = adapter
        }

        Retrofit().getUsersList { userList ->
            adapter.submitList(userList)
        }
    }

    private fun loadFragment(fragment: Fragment, id: Int) {
        fragment.arguments = Bundle().apply {
            putInt("id", id)
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .commit()
    }

}