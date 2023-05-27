package com.example.layout

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.retrofit2.R
import com.example.retrofit2.Retrofit
import com.example.retrofit2.databinding.FragmentUserBinding


class UserFragment : Fragment() {

    private lateinit var binding: FragmentUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = arguments?.getInt("id") ?: 0
        Retrofit().getUserById(id) { user ->
            binding.address.text = getString(R.string.address)
            binding.company.text = getString(R.string.company)
            binding.name.text = user.name
            binding.username.text = user.username
            binding.email.text = user.email
            binding.phone.text = user.phone
            binding.website.text = user.website
            binding.street.text = user.address.street
            binding.suite.text = user.address.suite
            binding.city.text = user.address.city
            binding.zipcode.text = user.address.zipcode
            binding.nameCompany.text = user.company.name
            binding.catchPhrase.text = user.company.catchPhrase
            binding.bs.text = user.company.bs
        }
    }
}