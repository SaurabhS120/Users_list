package com.example.user.users_domain.responses

import com.example.user.users_domain.entities.UsersEntity

open class UsersResponse(var response : List<UsersEntity>? = null,var errorMsg:String = "") {
    class Success(response: List<UsersEntity>?):UsersResponse(response)
    class Failure(errorMsg: String?):UsersResponse(errorMsg = errorMsg?:"")
}