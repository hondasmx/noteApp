package com.artyg.noteapp.service;

import com.artyg.noteapp.models.Note;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface NoteService {
    @NotNull List<Note> findAll();

    @Nullable Note findById(int id);

    void save(@NotNull Note note);

    void deleteById(int id);
}
