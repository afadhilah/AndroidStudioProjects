package com.example.loginmvvm

import android.app.Application
import com.example.loginmvvm.data.local.AppDatabase

class LoginApplication : Application() {
    val database: AppDatabase by lazy { AppDatabase.getDatabase(this) }
}
