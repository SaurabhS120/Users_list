package com.example.users.user_details

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.users.databinding.FragmentUserDetailsBinding
import com.example.users.users_list.UsersListViewModel

class UserDetailsFragment : Fragment() {
    lateinit var binding: FragmentUserDetailsBinding
    lateinit var usersListViewModel: UsersListViewModel
    override fun onAttach(context: Context) {
        super.onAttach(context)
        val usersListViewModel: UsersListViewModel by activityViewModels()
        this.usersListViewModel = usersListViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentUserDetailsBinding.inflate(layoutInflater, container, false)
        binding.usersListViewModel = usersListViewModel
        binding.lifecycleOwner = this
        return binding.root
    }
}