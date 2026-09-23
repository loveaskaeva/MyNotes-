package com.example.mynotes.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynotes.data.Note
import com.example.mynotes.data.NoteDatabase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class NoteViewModel(application: Application) : AndroidViewModel(application) {

    private val dao = NoteDatabase.getDatabase(application).noteDao()

    val notes: StateFlow<List<Note>> = dao.getAllNotes()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun addNote(title: String, content: String, colorHex: Long) {
        viewModelScope.launch {
            dao.insertNote(
                Note(
                    title = title,
                    content = content,
                    colorHex = colorHex,
                    timestamp = System.currentTimeMillis()
                )
            )
        }
    }

    suspend fun getNoteById(id: Int): Note? = dao.getNoteById(id)

    // Самостоятельное задание: удаление заметки
    fun deleteNote(note: Note) {
        viewModelScope.launch {
            dao.deleteNote(note)
        }
    }
}
