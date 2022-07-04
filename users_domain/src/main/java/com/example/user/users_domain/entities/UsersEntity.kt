package com.example.user.users_domain.entities

data class UsersEntity(
    val id: Int,
    val firstName: String,
    val maidenName: String,
    val lastName: String,
    val phone: String,
    val birthDate: String,
    val age: Int,
    val bloodGroup: String,
    val company: String
) {
    fun idString() = id.toString()
    fun fullName() = "$firstName $maidenName $lastName"
    fun ageString() = age.toString()

    companion object {
        fun getEmpty() = UsersEntity(-1, "", "", "", "", "", 0, "", "")
    }
}