package com.artyg.noteapp.service;

import com.artyg.noteapp.dao.NoteDAO;
import com.artyg.noteapp.models.Note;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class NoteServiceImpl implements NoteService {

    private final NoteDAO noteDAO;

    @Contract(pure = true)
    @Autowired
    public NoteServiceImpl(NoteDAO noteDAO) {
        this.noteDAO = noteDAO;
    }

    @NotNull
    @Override
    @Transactional
    public List<Note> findAll() {
        return noteDAO.findAll();
    }

    @Override
    @Nullable
    @Transactional
    public Note findById(int id) {
        return noteDAO.findById(id);
    }

    @Override
    @Transactional
    public void save(@NotNull Note note) {
        noteDAO.save(note);
    }

    @Transactional
    @Override
    public void deleteById(int id) {
        noteDAO.deleteById(id);
    }
}
