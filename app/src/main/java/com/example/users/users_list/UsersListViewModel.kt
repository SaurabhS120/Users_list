package com.example.users.users_list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.user.users_domain.responses.UsersResponse
import com.example.user.users_domain.usecases.GetUsersUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersListViewModel  @Inject constructor(val getUsersUsecase: GetUsersUsecase): ViewModel(){
    val users = MutableLiveData<UsersResponse>()
    fun getUsersUsecase() {
        viewModelScope.launch {
            users.postValue(getUsersUsecase.invoke())
        }
    }

}