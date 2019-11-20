package com.artyg.noteapp.dao;

import com.artyg.noteapp.models.Note;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;


@Repository
public class NoteDAOHibernateImpl implements NoteDAO {

    @NotNull private EntityManager entityManager;

    @Autowired
    public NoteDAOHibernateImpl(@NotNull EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @NotNull
    @Override
    public List<Note> findAll() {
        Session currentSession = entityManager.unwrap(Session.class);
        Query<Note> query = currentSession.createQuery("from Note order by creationDate desc", Note.class);
        return query.getResultList();
    }

    @Nullable
    @Override
    public Note findById(int id) {
        Session currentSession = entityManager.unwrap(Session.class);
        return currentSession.get(Note.class, id);
    }

    @Override
    public void save(@NotNull Note note) {
        Session currentSession = entityManager.unwrap(Session.class);
        currentSession.saveOrUpdate(note);
    }

    @Override
    public void deleteById(int noteId) {
        Session currentSession = entityManager.unwrap(Session.class);
        Query query = currentSession.createQuery("delete Note where id = :noteId");
        query.setParameter("noteId", noteId);
        query.executeUpdate();
    }
}
