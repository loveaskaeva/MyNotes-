package com.example.mynotes.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val content: String,
    val colorHex: Long = 0xFFFFF59D,
    // Самостоятельное задание: поле timestamp и его сохранение в БД
    val timestamp: Long = System.currentTimeMillis()
)
