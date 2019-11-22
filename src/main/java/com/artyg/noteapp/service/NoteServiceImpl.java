package com.artyg.noteapp.service;

import com.artyg.noteapp.dao.NoteRepository;
import com.artyg.noteapp.models.Note;
import com.artyg.noteapp.rest.NoteNotFoundException;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NoteServiceImpl implements NoteService {

    @NotNull
    private NoteRepository noteRepository;

    @Contract(pure = true)
    @Autowired
    public NoteServiceImpl(@NotNull NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    @NotNull
    @Override
    public List<Note> findAll() {
        return noteRepository.findAll(Sort.by("creationDate").descending());
    }

    @Override
    @Nullable
    public Note findById(int id) {
        Optional<Note> result = noteRepository.findById(id);

        Note note;
        if (result.isPresent()) {
            note = result.get();
        } else {
            throw new NoteNotFoundException("Did not find note id: " + id);
        }
        return note;
    }

    @Override
    public void save(@NotNull Note note) {
        noteRepository.save(note);
    }

    @Override
    public void deleteById(int id) {
        noteRepository.deleteById(id);
    }
}
