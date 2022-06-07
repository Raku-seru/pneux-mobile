package com.c22ps208.pneux.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

// Menyesuaikan response API login dan register endpoint
// Contoh :
@Parcelize
data class UserModel(
    val name: String,
    val email: String,
    val password: String,
    val userId: String,
    val token: String,
    val isLogin: Boolean
): Parcelable