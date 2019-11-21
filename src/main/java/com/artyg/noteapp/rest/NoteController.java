package com.artyg.noteapp.rest;

import com.artyg.noteapp.models.Note;
import com.artyg.noteapp.service.NoteService;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
public class NoteController {

    @NotNull
    private final NoteService noteService;

    @Contract(pure = true)
    public NoteController(@NotNull NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping("notes")
    @NotNull
    public List<Note> getNotes() {
        return noteService.findAll();
    }

    @GetMapping("notes/{noteId}")
    @Nullable
    public Note getNote(@PathVariable int noteId) {
        return noteService.findById(noteId);
    }

    @PostMapping("notes")
    @Nullable
    public Note saveNote(@NotNull @RequestBody Note note) {
        note.setCreationDate(new Date());
        noteService.save(note);
        return note;
    }

    @PutMapping("notes")
    @Nullable
    public Note updateNote(@RequestBody Note note) {
        noteService.save(note);
        return note;
    }

    @DeleteMapping("notes/{noteId}")
    public void deleteNote(@PathVariable int noteId) {
        noteService.deleteById(noteId);
    }
}
