package com.artyg.noteapp;

import com.artyg.noteapp.dao.NoteRepository;
import com.artyg.noteapp.models.Note;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@DataJpaTest
public class NoteRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private NoteRepository noteRepository;


    @Test
    public void findById() {
        Note note = new Note();
        note.setText("my text");
        note.setPriority("medium");
        entityManager.persist(note);
        entityManager.flush();
        Optional<Note> result = noteRepository.findById(note.getId());

        Note found = null;
        if (result.isPresent()) {
            found = result.get();
        }
        assert found != null;

        Assertions.assertThat(found.getText()).isEqualTo(note.getText());
    }

    @Test
    public void notFoundById() {
        Optional<Note> result = noteRepository.findById(1234567890);
        Assertions.assertThat(result.isEmpty());
    }

    @Test
    public void findAll() {
        Note note1 = new Note();
        Note note2 = new Note();
        entityManager.persist(note1);
        entityManager.persist(note2);
        entityManager.flush();

        List<Note> all = noteRepository.findAll();
        Assertions.assertThat(all.size() == 2);
        Assertions.assertThat(all.contains(note1) && all.contains(note2));
    }

    @Test
    public void deleteById() {
        Note note = new Note();
        entityManager.persist(note);
        entityManager.flush();

        noteRepository.deleteById(note.getId());
        List<Note> all = noteRepository.findAll();
        Assertions.assertThat(all.size() == 0);
    }
}
