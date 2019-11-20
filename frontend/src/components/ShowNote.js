import React, {Component} from "react";
import axios from "axios";
import {API_URL, NOTE} from "./NoteList";
import {formatDate} from "../Utils";

export class ShowNote extends Component {


    getNote() {

        axios.get(API_URL + NOTE + "/" + this.state.noteId)
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

        return (
            <div>
                <h3>Creation date: </h3> {creationDate}
                <h3>Text: </h3> {text}
            </div>
        )
    }

    errorPage() {
        return <div>
            Note with id: {this.state.noteId} was not found!
        </div>;
    }
}