package com.example.mynotes.ui

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mynotes.data.Note
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun NoteDetailScreen(
    noteId: Int,
    viewModel: NoteViewModel = viewModel(),
    onBack: () -> Unit
) {
    var note by remember { mutableStateOf<Note?>(null) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    val dateFormat = remember { SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault()) }

    // Самостоятельное задание: вывод в консоль ID заметки, пришедшего с первого экрана
    LaunchedEffect(noteId) {
        Log.d("MyNotesApp", "Получен ID заметки со второго экрана: $noteId")
        note = viewModel.getNoteById(noteId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Заметка") },
                navigationIcon = {
                    TextButton(onClick = onBack) {
                        Text("Назад")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            val currentNote = note
            if (currentNote == null) {
                Text("Заметка не найдена")
            } else {
                Text(currentNote.title, style = MaterialTheme.typography.headlineSmall)
                Spacer(modifier = Modifier.height(8.dp))
                Text(currentNote.content, style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "Создано: ${dateFormat.format(Date(currentNote.timestamp))}",
                    style = MaterialTheme.typography.labelMedium
                )
                Spacer(modifier = Modifier.height(24.dp))

                // Самостоятельное задание: удаление заметки
                Button(onClick = { showDeleteDialog = true }) {
                    Text("Удалить заметку")
                }
            }
        }
    }

    // Самостоятельное задание: AlertDialog с подтверждением удаления
    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Удаление заметки") },
            text = { Text("Вы уверены, что хотите удалить эту заметку?") },
            confirmButton = {
                TextButton(onClick = {
                    note?.let { viewModel.deleteNote(it) }
                    showDeleteDialog = false
                    onBack()
                }) {
                    Text("Удалить")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Отмена")
                }
            }
        )
    }
}
