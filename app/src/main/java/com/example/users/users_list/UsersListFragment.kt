package com.example.users.users_list

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.user.users_domain.responses.UsersResponse
import com.example.users.databinding.FragmentUsersListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UsersListFragment: Fragment() {
    val viewModel : UsersListViewModel by viewModels()
    val adapter = UsersListAdapter()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val binding = FragmentUsersListBinding.inflate(layoutInflater, container, false)
        binding.usersRecycler.adapter = adapter
        binding.usersRecycler.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getUsersUsecase()
        viewModel.users.observe(viewLifecycleOwner){response->
            when(response){
                is UsersResponse.Success->{
                    val data = response.response?: listOf()
                    adapter.submitList(data)
                }
                is UsersResponse.Failure->{
                    Toast.makeText(requireContext(),response.errorMsg,Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}