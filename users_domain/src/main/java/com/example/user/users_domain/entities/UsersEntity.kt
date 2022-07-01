package com.example.user.users_domain.entities

data class UsersEntity(
    val id: Int,
    val firstName: String,
    val maidenName: String,
    val lastName: String,
    val phone: String,
    val birthDate: String
) {
    fun idString() = id.toString()
    fun fullName() = "$firstName $maidenName $lastName"

    companion object {
        fun getEmpty() = UsersEntity(-1, "", "", "", "", "")
    }
}