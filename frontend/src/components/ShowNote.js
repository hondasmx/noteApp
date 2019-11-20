import React, {Component} from "react";
import axios from "axios";
import {API_URL, NOTE} from "./NoteList";
import {formatDate} from "../Utils";

export class ShowNote extends Component {

    noteId = this.props.match.params.number;

    getNote() {

        axios.get(API_URL + NOTE + "/" + this.noteId)
            .then((response) => {
                this.setState(() => ({
                    note: response.data
                }))
            })
            .catch(error => console.log(error));
    }

    constructor(props, context) {
        super(props, context);
        this.state = {note: undefined};
        this.getNote();
    }

    render() {
        const note = this.state.note;
        if (!note) return (
            <div>
                Note with id: {this.noteId} was not found!
            </div>
        );

        const creationDate = formatDate(note.creationDate);
        const text = note.text;

        return (
            <div>
                <h3>Creation date: </h3> {creationDate}
                <h3>Text: </h3> {text}
            </div>
        )
    }
}