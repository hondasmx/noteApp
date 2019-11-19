import React, {Component} from "react";
import axios from "axios";

const API_URL = "http://localhost:8080/api/";
const GET_TODOS = "todos";
const TODO = "todo";

export class TodoList extends Component {

    getTodos() {
        axios.get(API_URL + GET_TODOS)
            .then(response => {
                const data = response.data;
                const todos = data.map(el => {
                        return {
                            priority: el.priority,
                            text: el.text,
                            id: el.id,
                            date: el.creationDate,
                            isEdited: false
                        }
                    }
                );
                this.setState(() => ({
                    todos: todos
                }));
                console.log(todos)
            })
            .catch(error => {
                console.log(error);
            })
    }

    toggleEditMode(todoId) {
        const todos = this.state.todos.slice(0);
        let todo = todos.find(todo => todo.id === todoId);
        if (!todo) return;
        todo.isEdited = !todo.isEdited;
        this.setState(() => ({
            todos: todos
        }));

    }

    constructor(props) {
        super(props);
        this.state = {todos: []};
        this.getTodos();
    }

    render() {
        const todos = this.state.todos.map(todo => {
            return todo.isEdited ? this.editedTodoItem(todo) : this.todoItem(todo);
        });
        return <div>
            <h2>Todo list</h2>
            <div className="table-responsive">
                <table className="table table-striped table-sm">
                    <thead>
                    <tr>
                        <th>Creation date</th>
                        <th>Priority</th>
                        <th>Text</th>
                        <th/>
                        <th/>
                    </tr>
                    </thead>
                    <tbody>
                    {todos}
                    </tbody>
                </table>
            </div>
            <button type="button"
                    className="btn btn-primary"
                    onClick={this.addTodo.bind(this, {priority: "high", date: "date", text: "lorem ipsum"})}>Add todo
            </button>
        </div>
    }

    todoItem(todo) {
        return <tr>
            <td>{todo.date}</td>
            <td>{todo.priority}</td>
            <td>{todo.text}</td>
            <td>
                <button className="btn btn-danger" onClick={this.deleteTodo.bind(this, todo.id)}>Delete</button>
            </td>
            <td>
                <button className="btn btn-secondary" onClick={this.toggleEditMode.bind(this, todo.id)}>Edit</button>
            </td>
        </tr>;
    }

    editedTodoItem(todo) {
        const todoObj = {
            id: todo.id,
            priority: todo.priority,
            text: todo.text,
            date: todo.date
        };

        return <tr>
            <td> {todo.date}</td>
            <td>
                <div className="form-group">
                    <label htmlFor="exampleFormControlSelect">Priority</label>
                    <select className="form-control"
                        // value={priority}
                            id="exampleFormControlSelect"
                            onChange={(event) => todoObj.priority = event.target.value}
                            defaultValue={todo.priority}>
                        <option>low</option>
                        <option>medium</option>
                        <option>high</option>
                    </select>
                </div>
            </td>
            <td>
                <div className="form-group">
                    <label htmlFor="exampleFormControlTextarea">Example textarea</label>
                    <textarea className="form-control"
                              id="exampleFormControlTextarea"
                              rows="3"
                              onChange={(event) => todoObj.text = event.target.value}
                              defaultValue={todo.text}
                    />
                </div>
            </td>
            <td>
                <button className="btn btn-primary" onClick={this.saveTodo.bind(this, todoObj)}>Save</button>
            </td>
            <td>
                <button className="btn btn-secondary" onClick={this.toggleEditMode.bind(this, todo.id)}>Cancel</button>
            </td>
        </tr>;
    }

    saveTodo(todo) {

        let todoObject = {
            id: todo.id,
            priority: todo.priority,
            date: "date",
            text: todo.text
        };
        axios.put(API_URL + TODO, todoObject).then(() => {
            this.toggleEditMode(todo.id)
        }).then(() => {
            const todos = this.state.todos.slice(0);
            const t = todos.find(t=> t.id === todo.id);
            t.priority = todo.priority;
            t.text = todo.text;
            this.setState( ()=> ({
                todos: todos
            }))
        }).catch(error => console.log(error));
    }

    addTodo(todo) {
        axios.post(API_URL + TODO, {
            priority: todo.priority,
            date: todo.date,
            text: todo.text
        }).then((response) => {
            const todos = this.state.todos.slice(0);
            todos.push({id: response.data, priority: todo.priority, date: todo.date, text: todo.text});
            this.setState(() => ({
                todos: todos
            }));
        }).catch(error => console.log(error));
    }


    deleteTodo(todoId) {
        axios.delete(API_URL + TODO + "/" + todoId)
            .then(() => {
                let todos = this.state.todos.slice(0);
                todos = todos.filter(todo => todo.id !== todoId);
                this.setState(() => ({
                    todos: todos
                }));
            })
            .catch(error => {
                console.log(error);
            });


    }
}