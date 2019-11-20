import React, {Component} from "react";
import axios from "axios";
import {Link, NavLink} from 'react-router-dom'
import {formatDate} from "../Utils";

export const API_URL = "http://localhost:8080/api/";
export const GET_NOTES = "notes";
export const NOTE = "note";

export class NoteList extends Component {

    getNotes() {
        axios.get(API_URL + GET_NOTES)
            .then(response => {
                const data = response.data;
                const notes = data.map(el => {
                        return {
                            priority: el.priority,
                            text: el.text,
                            id: el.id,
                            creationDate: el.creationDate,
                            isEdited: false
                        }
                    }
                );
                this.setState(() => ({
                    notes: notes
                }));
            })
            .catch(error => {
                console.log(error);
            })
    }

    noteItem(note) {
        const creationDate = formatDate(note.creationDate);
        const noteId = note.id;
        return <tr>
            <td>{creationDate} <NavLink className="badge badge-secondary" to={"/showNote/" + noteId}>View</NavLink></td>
            <td>{note.priority}</td>
            <td className="word-break">{note.text}</td>
            <td>
                <button className="btn btn-secondary" onClick={this.toggleEditMode.bind(this, note.id)}>Edit</button>
            </td>
            <td>
                <button className="btn btn-danger" onClick={this.deleteNote.bind(this, note.id)}>Delete</button>
            </td>
        </tr>;
    }

    countChars(event) {
        const value = event.target.value;
        this.setState({
            chars: value.length
        });
    }

    editedNoteItem(note) {
        const noteObj = {
            id: note.id,
            priority: note.priority,
            text: note.text,
            creationDate: note.creationDate
        };
        const creationDate = formatDate(note.creationDate);
        return <tr>
            <td> {creationDate}</td>
            <td>
                <div className="form-group">
                    <label htmlFor="exampleFormControlSelect">Priority</label>
                    <select className="form-control"
                            id="exampleFormControlSelect"
                            onChange={(event) => noteObj.priority = event.target.value}
                            defaultValue={note.priority}>
                        <option>low</option>
                        <option>medium</option>
                        <option>high</option>
                    </select>
                </div>
            </td>
            <td>
                <div className="form-group">
                    <label htmlFor="exampleFormControlTextarea">Text</label>
                    <textarea className="form-control"
                              id="exampleFormControlTextarea"
                              rows="3"
                              onKeyUp={(event) => noteObj.text = event.target.value}
                              onChange={this.countChars.bind(this)}
                              defaultValue={note.text}
                    />

                </div>
            </td>
            <td>
                <button className="btn btn-primary" onClick={this.saveNote.bind(this, noteObj)}>Save</button>
            </td>
            <td>
                <button className="btn btn-secondary" onClick={this.toggleEditMode.bind(this, note.id)}>Cancel</button>
            </td>
        </tr>;
    }


    toggleEditMode(noteId) {
        const notes = this.state.notes.slice(0);
        let note = notes.find(note => note.id === noteId);
        if (!note) return;
        note.isEdited = !note.isEdited;
        this.setState(() => ({
            notes: notes
        }));

    }

    constructor(props) {
        super(props);
        this.state = {notes: []};
        this.getNotes();
    }

    render() {
        const notes = this.state.notes.map(note => {
            return note.isEdited ? this.editedNoteItem(note) : this.noteItem(note);
        });
        return <div>
            <h2>Note list</h2>
            <div className="table-responsive">
                <table className="table table-striped table-hover">
                    <thead className="thead-dark">
                    <tr>
                        <th>Creation date</th>
                        <th>Priority</th>
                        <th>Text</th>
                        <th/>
                        <th/>
                    </tr>
                    </thead>
                    <tbody>
                    {notes}
                    </tbody>
                </table>
            </div>
            <ul className="nav nav-pills justify-content-center">
                <li className="nav-item">
                    <Link className="nav-link active" to='/addNote'>Add Note</Link>
                </li>
            </ul>
        </div>
    }


    saveNote(note) {
        console.log(note);
        let noteObj = {
            id: note.id,
            priority: note.priority,
            creationDate: note.creationDate,
            text: note.text
        };
        axios.put(API_URL + NOTE, noteObj)
            .then(() => {
                this.toggleEditMode(note.id)
            })
            .then(() => {
                const notes = this.state.notes.slice(0);
                const t = notes.find(t => t.id === note.id);
                if (!t) return;

                t.priority = note.priority;
                t.text = note.text;
                t.creationDate = note.creationDate;
                this.setState(() => ({
                    notes: notes
                }))
            })
            .catch(error => console.log(error));
    }

    deleteNote(noteId) {
        axios.delete(API_URL + NOTE + "/" + noteId)
            .then(() => {
                let notes = this.state.notes.slice(0);
                notes = notes.filter(note => note.id !== noteId);
                this.setState(() => ({
                    notes: notes
                }));
            })
            .catch(error => {
                console.log(error);
            });
    }
}


