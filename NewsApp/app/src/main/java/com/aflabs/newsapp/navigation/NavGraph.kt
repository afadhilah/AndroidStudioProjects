package com.aflabs.newsapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.aflabs.newsapp.data.model.Article
import com.aflabs.newsapp.ui.screens.DetailScreen
import com.aflabs.newsapp.ui.screens.HomeScreen
import com.aflabs.newsapp.ui.screens.WebViewScreen
import com.aflabs.newsapp.viewmodel.NewsViewModel
import com.google.gson.Gson
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()
    val viewModel: NewsViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            HomeScreen(viewModel = viewModel) { article ->
                val articleJson = Gson().toJson(article)
                val encodedJson = URLEncoder.encode(articleJson, StandardCharsets.UTF_8.name())
                navController.navigate("detail/$encodedJson")
            }
        }
        composable(
            route = "detail/{articleJson}",
            arguments = listOf(navArgument("articleJson") { type = NavType.StringType })
        ) { backStackEntry ->
            val encodedJson = backStackEntry.arguments?.getString("articleJson") ?: ""
            val articleJson = URLDecoder.decode(encodedJson, StandardCharsets.UTF_8.name())
            val article = Gson().fromJson(articleJson, Article::class.java)

            val savedArticles by viewModel.savedArticles.collectAsState()
            val isSaved = savedArticles.any { it.title == article.title }

            DetailScreen(
                article = article,
                isSaved = isSaved,
                onSaveToggle = { viewModel.toggleSaveArticle(article) },
                onBackClick = { navController.popBackStack() },
                onReadFullArticleClick = { url, title ->
                    val encodedUrl = URLEncoder.encode(url, StandardCharsets.UTF_8.name())
                    val encodedTitle = URLEncoder.encode(title, StandardCharsets.UTF_8.name())
                    navController.navigate("webview/$encodedUrl/$encodedTitle")
                }
            )
        }
        composable(
            route = "webview/{url}/{title}",
            arguments = listOf(
                navArgument("url") { type = NavType.StringType },
                navArgument("title") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val encodedUrl = backStackEntry.arguments?.getString("url") ?: ""
            val encodedTitle = backStackEntry.arguments?.getString("title") ?: ""
            val url = URLDecoder.decode(encodedUrl, StandardCharsets.UTF_8.name())
            val title = URLDecoder.decode(encodedTitle, StandardCharsets.UTF_8.name())
            
            WebViewScreen(
                url = url,
                title = title,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}
