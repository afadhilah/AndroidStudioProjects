package com.aflabs.newsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.aflabs.newsapp.navigation.AppNavGraph
import com.aflabs.newsapp.ui.theme.NewsAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsAppTheme(darkTheme = false) {
                AppNavGraph()
            }
        }
    }
}
