package com.api.project.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import com.api.project.exceptions.NotFoundException;
import com.api.project.model.dto.NoteDTO;
import com.api.project.model.entity.Note;
import com.api.project.service.NoteService;
import java.util.List;

@RestController
@RequestMapping("/api/notes")
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @PostMapping("/create")
    public ResponseEntity<Void> create(@Valid @RequestBody NoteDTO noteDTO) {
        try {
            noteService.createNote(noteDTO);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("")
    public ResponseEntity<List<Note>> getAllNotes() {
        List<Note> notes = noteService.getNotes();
        return ResponseEntity.ok(notes);
    }

    @GetMapping("/{noteId}")
    public ResponseEntity<Note> getNote(@PathVariable Long noteId) {
        try {
            Note note = noteService.getNoteById(noteId);
            return ResponseEntity.ok(note);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("/{noteId}")
    public ResponseEntity<Note> updateNote(@PathVariable Long noteId, @RequestBody NoteDTO noteDTO) {
        try {
            Note updatedNote = noteService.updateNote(noteId, noteDTO);
            return ResponseEntity.ok().body(updatedNote);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{noteId}")
    public ResponseEntity<Void> deleteNote(@PathVariable Long noteId) {
        try {
            noteService.deleteNote(noteId);
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}