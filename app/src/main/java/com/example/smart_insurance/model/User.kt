package com.example.smart_insurance.model

import java.io.Serializable

data class User(
    val id: String,
    val name: String,
    val lassName: String,
    val dateOfBirth: String,
    val cc: String,
    val email: String,
    val profileImage: String? = null,
    val insurance: List<Insurance>? = null

) : Serializable {
    override fun toString(): String {
        return super.toString()
    }

    constructor() : this("", "", "", "", "", "")

}
