package com.artyg.noteapp;

import com.artyg.noteapp.dao.NoteRepository;
import com.artyg.noteapp.models.Note;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.UnsupportedEncodingException;
import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class NoteControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private NoteRepository noteRepository;

    @NotNull
    private Note insertNote() {
        Note note = new Note();
        note.setText("Test note ");
        noteRepository.save(note);
        return note;
    }

    private void deleteNote(@NotNull Note note) {
        noteRepository.deleteById(note.getId());
    }

    private void deleteNoteByResponseId(@NotNull MvcResult mvcResult) throws UnsupportedEncodingException, JSONException {
        JSONObject jsonObject = new JSONObject(mvcResult.getResponse().getContentAsString());
        int id = (int) jsonObject.get("id");
        noteRepository.deleteById(id);
    }


    @Test
    public void testGetNotes() throws Exception {
        Note note = insertNote();
        mvc.perform(get("/api/notes")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(note.getText())));
        deleteNote(note);
    }

    @Test
    public void testGetNote() throws Exception {
        Note note = insertNote();
        mvc.perform(get("/api/notes/" + note.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(note.getText())));
        deleteNote(note);
    }

    @Test
    public void testGetNoteWithStringId() throws Exception {
        mvc.perform(get("/api/notes/invalidId")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetNoteWithNonExistedId() throws Exception {
        Note note = insertNote();
        int id = note.getId();
        deleteNote(note);
        mvc.perform(get("/api/notes/" + id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Did not find note id: " + id)));
    }

    @Test
    public void testDeleteNoteById() throws Exception {
        Note note = insertNote();
        int id = note.getId();
        mvc.perform(delete("/api/notes/" + id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(""));

    }

    @Test
    public void testDeleteNoteByNonExistingId() throws Exception {
        Note note = insertNote();
        int id = note.getId();
        deleteNote(note);
        mvc.perform(delete("/api/notes/" + id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("No class com.artyg.noteapp.models.Note entity with id " + id + " exists!")));
    }

    @Test
    public void testDeleteNoteByStringId() throws Exception {
        mvc.perform(delete("/api/notes/invalidId")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Failed to convert value of type 'java.lang.String' to required type 'int'")));
    }

    @Test
    public void testPostNote() throws Exception {
        Note note = new Note();
        note.setText("postNote text");
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(note);
        MvcResult mvcResult = mvc.perform(post("/api/notes")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"text\":\"" + note.getText())))
                .andExpect(content().string(containsString("priority"))).andReturn();

        deleteNoteByResponseId(mvcResult);
    }

    @Test
    public void testPostNoteInvalidObject() throws Exception {
        String obj = "Hello world";
        mvc.perform(post("/api/notes")
                .content(obj)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testPostNoteLargeText() throws Exception {
        // The text field limit is 500 characters

        Note note = new Note();
        note.setText("i".repeat(501));
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(note);
        mvc.perform(post("/api/notes")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testPostNoteMaximumText() throws Exception {
        // The text field limit is 500 characters

        Note note = new Note();
        note.setText("i".repeat(500));
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(note);
        MvcResult mvcResult = mvc.perform(post("/api/notes")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        deleteNoteByResponseId(mvcResult);
    }

    @Test
    public void testPutNote() throws Exception {
        Note note = insertNote();
        String newPriority = "high";
        String newText = "new text";
        note.setPriority(newPriority);
        note.setText(newText);
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(note);

        mvc.perform(put("/api/notes")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        Optional<Note> found = noteRepository.findById(note.getId());
        assert found.isPresent();
        Note foundNote = found.get();
        Assertions.assertThat(foundNote.getPriority().equals(newPriority));
        assert foundNote.getText() != null;
        Assertions.assertThat(foundNote.getText().equals(newText));
        deleteNote(note);
    }

    @Test
    public void testPutNoteInvalidObject() throws Exception {
        String obj = "test";
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(obj);
        mvc.perform(put("/api/notes")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testPutNoteUpdateWithInvalidPriority() throws Exception {
        Note note = insertNote();
        String newPriority = "very high";
        note.setPriority(newPriority);
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(note);

        mvc.perform(put("/api/notes")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(containsString("could not execute statement")))
                .andExpect(status().isBadRequest());

        deleteNote(note);
    }

    @Test
    public void testPutNoteUpdateWithLargeText() throws Exception {
        Note note = insertNote();
        note.setText("i".repeat(501));
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(note);

        mvc.perform(put("/api/notes")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(containsString("could not execute statement")))
                .andExpect(status().isBadRequest());

        deleteNote(note);
    }
}
