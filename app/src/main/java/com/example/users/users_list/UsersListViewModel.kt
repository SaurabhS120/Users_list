package com.example.users.users_list

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.user.users_domain.entities.UsersEntity
import com.example.user.users_domain.entities.UsersEntityPage
import com.example.user.users_domain.usecases.GetUsersUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersListViewModel  @Inject constructor(val getUsersUsecase: GetUsersUsecase): ViewModel() {
    val users = MutableLiveData<List<UsersEntity>>()
    val compositeDisposable = CompositeDisposable()
    fun getUsersUsecase() {
        viewModelScope.launch(Dispatchers.IO) {
            getUsersUsecase.invoke(coroutineContext, compositeDisposable)
                .subscribe(getUsersObserver())
        }
    }

    private fun getUsersObserver(): Observer<UsersEntityPage> {
        return object : Observer<UsersEntityPage> {
            var list = mutableListOf<UsersEntity>()
            override fun onSubscribe(d: Disposable) {
                Log.d("state", "mediator observer onSubscribe")
                compositeDisposable.add(d)
            }

            override fun onNext(t: UsersEntityPage) {
                Log.d("state", "mediator observer onNext")
                list.addAll(t.entities)
            }

            override fun onError(e: Throwable) {
                Log.d("state", "mediator observer onError")
            }

            override fun onComplete() {
                Log.d("state", "mediator observer onComplete")
                users.postValue(list)
                list = mutableListOf()
            }

        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("state", "disposing")
        compositeDisposable.dispose()
    }
}