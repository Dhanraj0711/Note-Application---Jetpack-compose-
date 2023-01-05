package com.example.noteapp.feature_note.domain.model

import androidx.compose.material.Colors
import androidx.compose.ui.graphics.Color
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note_table")
data class Note(
    val title: String,
    val content: String,
    val timestamp: Long,
    val color: Int,
    @ColumnInfo(name = "id")
    @PrimaryKey val id: Int? = null
) {
    companion object{
        val noteColors = listOf(
            Color(0xFFff5349),
            Color(0xFF90EE90),
            Color(0xFF8F00FF),
            Color(0xFFFFC0CB),
            Color(0xFFADD8E6),
            Color(0xFF311B92),
        )
    }
}

class InvalidNoteException(message: String) : Exception(message)