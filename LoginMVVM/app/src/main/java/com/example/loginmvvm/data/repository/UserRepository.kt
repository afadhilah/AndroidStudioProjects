package com.example.loginmvvm.data.repository

import com.example.loginmvvm.data.local.User
import com.example.loginmvvm.data.local.UserDao

class UserRepository(private val userDao: UserDao) {
    suspend fun getUserByUsername(username: String): User? {
        return userDao.getUserByUsername(username)
    }

    suspend fun insertUser(user: User): Long {
        return userDao.insertUser(user)
    }
}
