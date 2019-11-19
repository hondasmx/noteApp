package com.artyg.todoapp.service;

import com.artyg.todoapp.dao.TodoDAO;
import com.artyg.todoapp.models.Todo;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class TodoServiceImpl implements TodoService {

    private final TodoDAO todoDAO;

    public TodoServiceImpl(TodoDAO todoDAO) {
        this.todoDAO = todoDAO;
    }

    @NotNull
    @Override
    @Transactional
    public List<Todo> findAll() {
        return todoDAO.findAll();
    }

    @Override
    @Nullable
    @Transactional
    public Todo findById(int id) {
        return todoDAO.findById(id);
    }

    @Override
    @Transactional
    public void save(@NotNull Todo todo) {
        todoDAO.save(todo);
    }

    @Transactional
    @Override
    public void deleteById(int id) {
        todoDAO.deleteById(id);
    }
}
