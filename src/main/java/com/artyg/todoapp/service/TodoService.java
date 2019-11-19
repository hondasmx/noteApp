package com.artyg.todoapp.service;

import com.artyg.todoapp.models.Todo;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface TodoService {
    @NotNull List<Todo> findAll();

    @Nullable Todo findById(int id);

    void save(@NotNull Todo todo);

    void deleteById(int id);
}
