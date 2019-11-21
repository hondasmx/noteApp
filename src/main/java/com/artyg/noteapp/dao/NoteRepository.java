package com.artyg.noteapp.dao;

import com.artyg.noteapp.models.Note;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<Note, Integer> {
    
}
