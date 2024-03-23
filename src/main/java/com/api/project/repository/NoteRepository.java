package com.api.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.api.project.model.entity.Note;

public interface NoteRepository extends JpaRepository<Note, Long> {

}
