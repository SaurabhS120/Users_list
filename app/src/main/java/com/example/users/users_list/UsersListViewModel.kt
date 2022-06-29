package com.example.users.users_list

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.user.users_domain.entities.UsersEntity
import com.example.user.users_domain.usecases.GetUsersUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersListViewModel  @Inject constructor(val getUsersUsecase: GetUsersUsecase): ViewModel() {
    val users = MutableLiveData<List<UsersEntity>>()
    fun getUsersUsecase() {
        viewModelScope.launch(Dispatchers.IO) {
            getUsersUsecase.invoke(coroutineContext).subscribe(getUsersObserver())
        }
    }

    private fun getUsersObserver(): Observer<List<UsersEntity>> {
        return object : Observer<List<UsersEntity>> {
            override fun onSubscribe(d: Disposable) {
                Log.d("state", "mediator observer onSubscribe")
            }

            override fun onNext(t: List<UsersEntity>) {
                Log.d("state", "mediator observer onNext")
                users.postValue(t)
            }

            override fun onError(e: Throwable) {
                Log.d("state", "mediator observer onError")
            }

            override fun onComplete() {
                Log.d("state", "mediator observer onComplete")
            }

        }
    }

}