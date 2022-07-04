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
import kotlinx.coroutines.withContext
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
    val errorLiveData = MutableLiveData<String>()
    val selectedUserPos = MutableLiveData(-1)
    val selectedUser = MutableLiveData(UsersEntity.getEmpty())
    val progressText = MutableLiveData("Loading")

    init {
        Log.d("state", "viewmodel init")
        loadData()
        selectedUserPos.observeForever { pos ->
            users.value?.let { users ->
                if (pos >= 0) selectedUser.postValue(users.get(pos))
            }

        }
        progress.observeForever { count ->
            maxProgress.value?.let { maxProgress ->
                if (maxProgress > 0) {
                    val progress = count * 100 / maxProgress
                    progressText.postValue("$progress%")
                }
            }
        }
    }

    fun loadData() {
        viewModelScope.launch {
            isDataLoaded.postValue(false)
            if (getPageCount()) {
                getUsersUsecase()
            }
        }
    }

    suspend fun getUsersUsecase() {
        return withContext(Dispatchers.IO) {
            getUsersUsecase.invoke(compositeDisposable)
                .onErrorComplete {
                    Log.d("state", "getUsersUsecase fail")
                    errorLiveData.postValue(it.message)
                    true
                }
                .subscribe(getUsersObserver())
        }
    }

    suspend fun getPageCount(): Boolean {
        return withContext(Dispatchers.IO) {
            val maxP = getPageCountUsecase.invoke()
                .onErrorReturn {
                    isDataLoaded.postValue(true)
                    errorLiveData.postValue(it.message.toString())
                    -1
                }
            val value = maxP.blockingGet()
            maxProgress.postValue(value)
            Log.d("progress", "max : $value")
            value >= 1
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
                Log.d("data", t.entities.map { it.id }.toString())
                list.addAll(t.entities)
            }

            override fun onError(e: Throwable) {
                Log.d("state", "mediator observer onError")
                errorLiveData.postValue(e.message)
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