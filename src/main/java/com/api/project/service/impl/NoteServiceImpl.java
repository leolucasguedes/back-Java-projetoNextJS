package com.api.project.service.impl;

import org.springframework.stereotype.Service;
import com.api.project.model.dto.NoteDTO;
import com.api.project.model.entity.Note;
import com.api.project.model.entity.User;
import com.api.project.repository.NoteRepository;
import com.api.project.repository.UserRepository;
import com.api.project.service.NoteService;
import com.api.project.exceptions.NotFoundException;
import java.util.Optional;
import java.util.List;

@Service
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;
    private final UserRepository userRepository;

    public NoteServiceImpl(NoteRepository noteRepository, UserRepository userRepository) {
        this.noteRepository = noteRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Note createNote(NoteDTO noteDTO) {
        Optional<User> userOptional = userRepository.findById(noteDTO.getUserId());
        if (userOptional.isEmpty()) {
            throw new NotFoundException("User not found");
        }

        User user = userOptional.get();
        Note newNote = new Note(noteDTO.getTitle(), noteDTO.getText(), user);

        return noteRepository.save(newNote);
    }

    @Override
    public List<Note> getNotes() {
        return noteRepository.findAll();
    }

    @Override
    public Note getNoteById(Long noteId) {
        return noteRepository.findById(noteId)
                .orElseThrow(() -> new NotFoundException("Note not found"));
    }

    @Override
    public Note updateNote(Long noteId, NoteDTO noteDTO) {
        Note existingNote = noteRepository.findById(noteId)
                .orElseThrow(() -> new NotFoundException("Note not found"));

        existingNote.setTitle(noteDTO.getTitle());
        existingNote.setText(noteDTO.getText());

        return noteRepository.save(existingNote);
    }

    @Override
    public void deleteNote(Long noteId) {
        Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new NotFoundException("Note not found"));

        noteRepository.delete(note);
    }
}