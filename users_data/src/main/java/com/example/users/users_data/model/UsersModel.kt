package com.example.users.users_data.model

import com.google.gson.annotations.SerializedName

data class UsersModel(

	@SerializedName("total")
	val total: Int? = null,

	@SerializedName("limit")
	val limit: Int? = null,

	@SerializedName("skip")
	val skip: Int? = null,

	@SerializedName("users")
	val users: List<UsersItem?>? = null
)

data class Bank(

	@SerializedName("iban")
	val iban: String? = null,

	@SerializedName("cardExpire")
	val cardExpire: String? = null,

	@SerializedName("cardType")
	val cardType: String? = null,

	@SerializedName("currency")
	val currency: String? = null,

	@SerializedName("cardNumber")
	val cardNumber: String? = null
)

data class Coordinates(

	@SerializedName("lng")
	val lng: Double? = null,

	@SerializedName("lat")
	val lat: Double? = null
)

data class Hair(

	@SerializedName("color")
	val color: String? = null,

	@SerializedName("type")
	val type: String? = null
)

data class Company(

	@SerializedName("address")
	val address: Address? = null,

	@SerializedName("name")
	val name: String? = null,

	@SerializedName("department")
	val department: String? = null,

	@SerializedName("title")
	val title: String? = null
)

data class UsersItem(

	@SerializedName("lastName")
	val lastName: String? = null,

	@SerializedName("gender")
	val gender: String? = null,

	@SerializedName("university")
	val university: String? = null,

	@SerializedName("maidenName")
	val maidenName: String? = null,

	@SerializedName("ein")
	val ein: String? = null,

	@SerializedName("ssn")
	val ssn: String? = null,

	@SerializedName("bloodGroup")
	val bloodGroup: String? = null,

	@SerializedName("password")
	val password: String? = null,

	@SerializedName("hair")
	val hair: Hair? = null,

	@SerializedName("bank")
	val bank: Bank? = null,

	@SerializedName("eyeColor")
	val eyeColor: String? = null,

	@SerializedName("company")
	val company: Company? = null,

	@SerializedName("id")
	val id: Int? = null,

	@SerializedName("email")
	val email: String? = null,

	@SerializedName("height")
	val height: Int? = null,

	@SerializedName("image")
	val image: String? = null,

	@SerializedName("address")
	val address: Address? = null,

	@SerializedName("ip")
	val ip: String? = null,

	@SerializedName("weight")
	val weight: Double? = null,

	@SerializedName("userAgent")
	val userAgent: String? = null,

	@SerializedName("birthDate")
	val birthDate: String? = null,

	@SerializedName("firstName")
	val firstName: String? = null,

	@SerializedName("macAddress")
	val macAddress: String? = null,

	@SerializedName("phone")
	val phone: String? = null,

	@SerializedName("domain")
	val domain: String? = null,

	@SerializedName("age")
	val age: Int? = null,

	@SerializedName("username")
	val username: String? = null
)

data class Address(

	@SerializedName("address")
	val address: String? = null,

	@SerializedName("city")
	val city: String? = null,

	@SerializedName("postalCode")
	val postalCode: String? = null,

	@SerializedName("coordinates")
	val coordinates: Coordinates? = null,

	@SerializedName("state")
	val state: String? = null
)
