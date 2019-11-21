import React, {Component} from "react";
import axios from "axios";
import {API_URL} from "./NoteList";
import {formatDate} from "../Utils";
import {Link} from "react-router-dom";

export class ShowNote extends Component {

    getNote() {
        axios.get(API_URL + "/" + this.state.noteId)
            .then((response) => {
                this.setState(() => ({
                    note: response.data
                }))
            })
            .catch(error => console.log(error));
    }

    constructor(props, context) {
        super(props, context);
        const noteId = this.props.match.params.number;
        this.state = {note: undefined, noteId: noteId};
        this.getNote();
    }

    render() {
        const note = this.state.note;
        if (!note) return (
            this.errorPage()
        );

        const creationDate = formatDate(note.creationDate);
        const text = note.text;
        const priority = note.priority;

        return (
            <div>
                <br/>
                <br/>
                <h3>Creation date: </h3> {creationDate}
                <h3>Priority: </h3> {priority}
                <h3>Text: </h3> {text}
                <br/>

                <ul className="nav nav-pills justify-content-center">
                    <li className="nav-item">
                        <Link className="nav-link active" to='/'>Main page</Link>
                    </li>
                </ul>
            </div>
        )
    }

    errorPage() {
        return <div>
            Note with id: {this.state.noteId} was not found!
        </div>;
    }
}