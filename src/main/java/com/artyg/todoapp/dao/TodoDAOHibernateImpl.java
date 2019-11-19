package com.artyg.todoapp.dao;

import com.artyg.todoapp.models.Todo;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;


@Repository
public class TodoDAOHibernateImpl implements TodoDAO {

    @NotNull private EntityManager entityManager;

    @Autowired
    public TodoDAOHibernateImpl(@NotNull EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @NotNull
    @Override
    public List<Todo> findAll() {
        Session currentSession = entityManager.unwrap(Session.class);
        Query<Todo> query = currentSession.createQuery("from Todo", Todo.class);
        return query.getResultList();
    }

    @Nullable
    @Override
    public Todo findById(int id) {
        Session currentSession = entityManager.unwrap(Session.class);
        return currentSession.get(Todo.class, id);
    }

    @Override
    public void save(@NotNull Todo todo) {
        Session currentSession = entityManager.unwrap(Session.class);
        currentSession.saveOrUpdate(todo);
    }

    @Override
    public void deleteById(int todoId) {
        Session currentSession = entityManager.unwrap(Session.class);
        Query query = currentSession.createQuery("delete Todo where id = :todoId");
        query.setParameter("todoId", todoId);
        query.executeUpdate();
    }
}
