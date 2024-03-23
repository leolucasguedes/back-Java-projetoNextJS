package com.api.project.service;

import com.api.project.model.dto.NoteDTO;
import com.api.project.model.entity.Note;
import java.util.List;

public interface NoteService {
    Note createNote(NoteDTO noteDTO);

    List<Note> getNotes();

    Note getNoteById(Long noteId);

    Note updateNote(Long noteId, NoteDTO noteDTO);

    void deleteNote(Long noteId);
}
