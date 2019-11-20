package com.artyg.noteapp.dao;

import com.artyg.noteapp.models.Note;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface NoteDAO {
    @NotNull List<Note> findAll();

    @Nullable Note findById(int id);

    void save(@NotNull Note note);

    void deleteById(int id);
}
