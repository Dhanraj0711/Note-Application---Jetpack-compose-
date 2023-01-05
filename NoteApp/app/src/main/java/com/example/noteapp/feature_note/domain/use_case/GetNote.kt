package com.example.noteapp.feature_note.domain.use_case

import com.example.noteapp.feature_note.domain.model.InvalidNoteException
import com.example.noteapp.feature_note.domain.model.Note
import com.example.noteapp.feature_note.domain.repository.NoteRepository

class GetNote(val repository: NoteRepository) {

    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(id: Int): Note? {
        return repository.getNoteByID(id)
    }
}