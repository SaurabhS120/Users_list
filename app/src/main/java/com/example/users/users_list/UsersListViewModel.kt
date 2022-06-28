package com.example.users.users_list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.user.users_domain.entities.UsersEntity
import com.example.user.users_domain.usecases.GetUsersUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersListViewModel  @Inject constructor(val getUsersUsecase: GetUsersUsecase): ViewModel(){
    val users = MutableLiveData<List<UsersEntity>>()
    fun getUsersUsecase() {
        viewModelScope.launch {
            getUsersUsecase.invoke().observeForever {
                users.postValue(it)
            }
        }
    }

}