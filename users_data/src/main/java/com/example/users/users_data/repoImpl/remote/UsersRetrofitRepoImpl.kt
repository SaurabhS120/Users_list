package com.example.users.users_data.repoImpl.remote

import com.example.user.users_domain.entities.UsersEntity
import com.example.users.users_data.api.remote.ApiInterface
import com.example.users.users_data.mappers.UsersMapper
import com.example.users.users_data.model.UsersModel
import com.example.users.users_data.repos.UsersRemoteRepo
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@Singleton
class UsersRetrofitRepoImpl @Inject constructor(val api: ApiInterface) : UsersRemoteRepo {
    override suspend fun getUsers(
        coroutineContext: CoroutineContext,
        compositeDisposable: CompositeDisposable
    ): Single<List<UsersEntity>> {
        val response = api.getUsers()
        return Single.create { singleEmitter ->
            if (response.isSuccessful) {
                val responseData: UsersModel? = response.body()
                responseData?.let {
                    val entities = UsersMapper.toUsersEntities(it)
                    singleEmitter.onSuccess(entities)
                }
            }
        }
    }

}