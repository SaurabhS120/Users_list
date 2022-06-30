package com.example.users.users_data.repoImpl.remote

import android.util.Log
import com.example.user.users_domain.entities.UsersEntityPage
import com.example.users.users_data.PageConfig
import com.example.users.users_data.api.remote.ApiInterface
import com.example.users.users_data.mappers.UsersMapper
import com.example.users.users_data.model.UsersModel
import com.example.users.users_data.repos.UsersRemoteRepo
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@Singleton
class UsersRetrofitRepoImpl @Inject constructor(val api: ApiInterface) : UsersRemoteRepo {
//    override suspend fun getUsers(
//        coroutineContext: CoroutineContext,
//        compositeDisposable: CompositeDisposable
//    ): Single<List<UsersEntity>> {
//        val response = api.getUsers()
//        return Single.create { singleEmitter ->
//            if (response.isSuccessful) {
//                val responseData: UsersModel? = response.body()
//                responseData?.let {
//                    val entities = UsersMapper.toUsersEntities(it)
//                    singleEmitter.onSuccess(entities)
//                }
//            }
//        }
//    }

    override fun getUsersPage(
        pageNo: Int,
        coroutineContext: CoroutineContext,
        compositeDisposable: CompositeDisposable
    ): Single<UsersEntityPage> {
        val offset = ((pageNo - 1) * PageConfig.PAGE_SIZE) + 1
        Log.d("state", "getUserRetrofit page:$pageNo")
        Log.d("state", "getUserRetrofit offset:$offset pagesize:${PageConfig.PAGE_SIZE}")
        return Single.create { singleEmitter ->
            CoroutineScope(coroutineContext).launch {
                val response = api.getUsers(offset, PageConfig.PAGE_SIZE)
                if (response.isSuccessful) {
                    val responseData: UsersModel? = response.body()
                    responseData?.let {
                        val entities = UsersMapper.toUsersEntities(it)
                        val text: Int? = if (entities.size >= 1) {
                            entities[0].id
                        } else {
                            null
                        }
                        Log.d("state", "getUserRetrofit success id:$text")
                        val usersEntityPage = UsersEntityPage(pageNo, entities)
                        singleEmitter.onSuccess(usersEntityPage)
                        return@launch
                    }
                }
                singleEmitter.onError(Throwable(response.errorBody().toString()))
            }
        }
    }

    override fun getPageCount(): Single<Int> {
        return Single.create { emitter ->
            api.getUsersEmpty()
                .enqueue(object : Callback<UsersModel> {
                    override fun onResponse(
                        call: Call<UsersModel>,
                        response: retrofit2.Response<UsersModel>
                    ) {
                        if (response.isSuccessful) {
                            val total = response.body()?.total ?: 0
                            if (total < 1) {
                                emitter.onSuccess(-1)
                            } else {
                                emitter.onSuccess(total / PageConfig.PAGE_SIZE)
                            }
                        }
                    }

                    override fun onFailure(call: Call<UsersModel>, t: Throwable) {
                        if (emitter.isDisposed.not()) {
                            emitter.onError(Throwable("Network error"))
                        }
                    }
                })
        }


//        }

//            .compose {
//            val response = it
//                .onErrorReturn {
//                    Log.d("state","network onErrorReturn")
//                    null
//                }
//                .blockingGet()
//            Single.just(
//                if (response.isSuccessful){
//                    response.body()?.total?.let {
//                        if (it>0)it/PageConfig.PAGE_SIZE
//                        else -1
//                    }?:-1
//                }else{
//                    -1
//                }
//            )
//        }
//        if (response.isSuccessful) {
//            val responseData: UsersModel? = response.body()
//            responseData?.total?.let {
//                return it / PageConfig.PAGE_SIZE
//            }
//        }
    }

    override suspend fun getUsers(
        coroutineContext: CoroutineContext,
        compositeDisposable: CompositeDisposable
    ): Single<UsersEntityPage> {
        TODO("Not yet implemented")
    }

}