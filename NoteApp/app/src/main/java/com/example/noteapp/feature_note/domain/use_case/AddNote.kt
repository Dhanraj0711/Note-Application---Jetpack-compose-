package com.example.noteapp.feature_note.domain.use_case

import com.example.noteapp.feature_note.domain.model.InvalidNoteException
import com.example.noteapp.feature_note.domain.model.Note
import com.example.noteapp.feature_note.domain.repository.NoteRepository

class AddNote(private val repository: NoteRepository) {

    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note: Note) {
        if (note.title.isBlank()) {
            throw  InvalidNoteException("title of the note can't be Empty.")
        }
        if (note.content.isBlank()) {
            throw  InvalidNoteException("content of the note can't be Empty.")
        }
        repository.insertNote(note)
    }
}