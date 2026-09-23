package com.example.mynotes.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mynotes.data.Note
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private val screenColors = listOf(
    Color(0xFFFFFFFF), Color(0xFFE3F2FD), Color(0xFFFFF3E0), Color(0xFFE8F5E9)
)
private val buttonColors = listOf(
    Color(0xFF1976D2), Color(0xFFD32F2F), Color(0xFF388E3C), Color(0xFF7B1FA2)
)
private val cardColors = listOf(0xFFFFF59D, 0xFFFFCCBC, 0xFFC8E6C9, 0xFFBBDEFB)

@Composable
fun NotesListScreen(
    viewModel: NoteViewModel = viewModel(),
    onNoteClick: (Int) -> Unit
) {
    val notes by viewModel.notes.collectAsState()

    var screenColorIndex by remember { mutableStateOf(0) }
    var buttonColorIndex by remember { mutableStateOf(0) }
    var inputText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(screenColors[screenColorIndex])
            .padding(16.dp)
    ) {
        Text("Мои заметки", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(12.dp))

        // Самостоятельное задание: смена цвета кнопки и цвета фона экрана
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = {
                screenColorIndex = (screenColorIndex + 1) % screenColors.size
            }) {
                Text("Сменить фон")
            }
            Button(
                onClick = { buttonColorIndex = (buttonColorIndex + 1) % buttonColors.size },
                colors = ButtonDefaults.buttonColors(containerColor = buttonColors[buttonColorIndex])
            ) {
                Text("Цвет кнопки")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = inputText,
            onValueChange = { inputText = it },
            label = { Text("Текст заметки") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            // Самостоятельное задание: вывод в консоль текста из поля ввода
            Button(
                onClick = { Log.d("MyNotesApp", "Введённый текст: $inputText") },
                colors = ButtonDefaults.buttonColors(containerColor = buttonColors[buttonColorIndex])
            ) {
                Text("В консоль")
            }

            Button(
                onClick = {
                    if (inputText.isNotBlank()) {
                        val color = cardColors.random()
                        viewModel.addNote(
                            title = inputText.take(20),
                            content = inputText,
                            colorHex = color
                        )
                        inputText = ""
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = buttonColors[buttonColorIndex])
            ) {
                Text("Добавить")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(notes, key = { it.id }) { note ->
                NoteCard(note = note, onClick = { onNoteClick(note.id) })
            }
        }
    }
}

@Composable
private fun NoteCard(note: Note, onClick: () -> Unit) {
    // Самостоятельное задание: цвет карточки заметки + дата
    val dateFormat = remember { SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault()) }
    Card(
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(note.colorHex)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(note.title, style = MaterialTheme.typography.titleMedium)
            Text(note.content, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = dateFormat.format(Date(note.timestamp)),
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}
