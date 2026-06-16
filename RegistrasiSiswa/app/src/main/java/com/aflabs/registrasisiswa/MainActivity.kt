package com.aflabs.registrasisiswa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.aflabs.registrasisiswa.data.AppDatabase
import com.aflabs.registrasisiswa.ui.MainScreen
import com.aflabs.registrasisiswa.ui.StudentAppTheme
import com.aflabs.registrasisiswa.viewmodel.StudentViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dao = AppDatabase
            .getDatabase(applicationContext)
            .siswaDao()

        setContent {
            StudentAppTheme {
                val viewModel = StudentViewModel(dao)
                MainScreen(viewModel)
            }
        }
    }
}
