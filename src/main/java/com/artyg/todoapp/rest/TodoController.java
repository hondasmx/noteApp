package com.artyg.todoapp.rest;

import com.artyg.todoapp.models.Todo;
import com.artyg.todoapp.service.TodoService;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
public class TodoController {

    @NotNull
    private final TodoService todoService;

    @Contract(pure = true)
    public TodoController(@NotNull TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping("todos")
    @NotNull
    public List<Todo> getTodos() {
        return todoService.findAll();
    }

    @GetMapping("todo/{todoId}")
    @Nullable
    public Todo getTodo(@PathVariable String todoId) {
        try {
            int id = Integer.parseInt(todoId);
            return todoService.findById(id);
        } catch (NumberFormatException e) {
            return null;
        }

    }

    @PostMapping("todo")
    public long saveTodo(@RequestBody Todo todo) {
        todo.setId(0);
        todo.setCreationDate(new Date());
        todoService.save(todo);
        return todo.getId();
    }

    @PutMapping("todo")
    public Todo updateTodo(@RequestBody Todo todo) {
        todoService.save(todo);
        return todo;
    }

    @DeleteMapping("todo/{todoId}")
    public void deleteTodo(@PathVariable String todoId) {
        try {
            int id = Integer.parseInt(todoId);
            todoService.deleteById(id);
        } catch (NumberFormatException e) {
            //nothing
        }
    }
}
