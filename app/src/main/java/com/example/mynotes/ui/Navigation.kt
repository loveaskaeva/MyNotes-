package com.example.mynotes.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun NoteNavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = "list") {

        composable("list") {
            NotesListScreen(
                onNoteClick = { noteId ->
                    navController.navigate("detail/$noteId")
                }
            )
        }

        composable("detail/{noteId}") { backStackEntry ->
            val noteId = backStackEntry.arguments?.getString("noteId")?.toIntOrNull() ?: -1
            NoteDetailScreen(
                noteId = noteId,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
