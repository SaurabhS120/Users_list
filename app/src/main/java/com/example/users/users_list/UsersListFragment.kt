package com.example.users.users_list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.users.R
import com.example.users.databinding.FragmentUsersListBinding

class UsersListFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val binding = FragmentUsersListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}