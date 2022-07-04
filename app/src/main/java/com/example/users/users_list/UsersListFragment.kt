package com.example.users.users_list

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.users.MainActivity
import com.example.users.R
import com.example.users.databinding.FragmentUsersListBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UsersListFragment: Fragment() {
    lateinit var viewModel: UsersListViewModel
    lateinit var adapter: UsersListAdapter
    lateinit var binding: FragmentUsersListBinding
    lateinit var activity: MainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val viewModel: UsersListViewModel by activityViewModels()
        this.viewModel = viewModel
        activity = requireActivity() as MainActivity
        adapter = UsersListAdapter(viewModel.selectedUserPos, activity.theme)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentUsersListBinding.inflate(layoutInflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.usersRecycler.adapter = adapter
        binding.usersRecycler.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.users.observe(viewLifecycleOwner) { response ->
            adapter.submitList(response)
        }
        viewModel.errorLiveData.observe(viewLifecycleOwner) {
            Snackbar.make(binding.root, it, Snackbar.LENGTH_INDEFINITE)
                .setAction("Retry", {
                    viewModel.loadData()
                })
                .show()
        }
        viewModel.selectedUserPos.value = -1
        viewModel.selectedUserPos.observe(viewLifecycleOwner) {
            if (it >= 0) {
                activity.getNavController().navigate(R.id.users_details)
            }
        }
    }
}