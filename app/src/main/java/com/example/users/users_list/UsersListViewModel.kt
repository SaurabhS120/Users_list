package com.example.users.users_list

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.user.users_domain.entities.UsersEntity
import com.example.user.users_domain.entities.UsersEntityPage
import com.example.user.users_domain.usecases.GetPageCountUsecase
import com.example.user.users_domain.usecases.GetUsersUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersListViewModel @Inject constructor(
    val getUsersUsecase: GetUsersUsecase,
    val getPageCountUsecase: GetPageCountUsecase
) : ViewModel() {
    val users = MutableLiveData<List<UsersEntity>>()
    val isDataLoaded = MutableLiveData(false)
    val progress = MutableLiveData(0)
    val maxProgress = MutableLiveData(1)
    val compositeDisposable = CompositeDisposable()

    init {
        getPageCount()
        getUsersUsecase()
    }

    fun getUsersUsecase() {
        viewModelScope.launch(Dispatchers.IO) {
            getUsersUsecase.invoke(coroutineContext, compositeDisposable)
                .subscribe(getUsersObserver())
        }
    }

    fun getPageCount() {
        viewModelScope.launch(Dispatchers.IO) {
            val maxP = getPageCountUsecase.invoke()
            maxProgress.postValue(maxP)
            Log.d("progress", "max : $maxP")
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
                Log.d("progress", t.pageNo.toString())
                progress.postValue(t.pageNo)
                list.addAll(t.entities)
            }

            override fun onError(e: Throwable) {
                Log.d("state", "mediator observer onError")
            }

            override fun onComplete() {
                Log.d("state", "mediator observer onComplete")
                users.postValue(list)
                isDataLoaded.postValue(true)
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