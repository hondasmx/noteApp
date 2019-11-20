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

    @GetMapping("note/{noteId}")
    @Nullable
    public Note getNote(@PathVariable String noteId) {
        try {
            int id = Integer.parseInt(noteId);
            return noteService.findById(id);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @PostMapping("note")
    public long saveNote(@RequestBody Note note) {
        note.setId(0);
        note.setCreationDate(new Date());
        noteService.save(note);
        return note.getId();
    }

    @PutMapping("note")
    public Note updateNote(@RequestBody Note note) {
        noteService.save(note);
        return note;
    }

    @DeleteMapping("note/{noteId}")
    public void deleteNote(@PathVariable String noteId) {
        try {
            int id = Integer.parseInt(noteId);
            noteService.deleteById(id);
        } catch (NumberFormatException e) {
            //nothing
        }
    }
}
