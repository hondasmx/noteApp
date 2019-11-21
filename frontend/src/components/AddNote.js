import React, {Component} from "react";
import {Link, Redirect} from "react-router-dom";
import axios from "axios";
import {API_URL} from "./NoteList";


export class AddNote extends Component {


    constructor(props, context) {
        super(props, context);
        this.state = {redirect: false, chars: 0}
    }

    addNote(note) {
        axios.post(API_URL, {
            priority: note.priority,
            text: note.text
        }).then((response) => {
            if (!response.data) return;

            this.setState(() => ({
                redirect: true
            }));

        }).catch(error => console.log(error));
    }

    render() {
        return (
            this.state.redirect ? <Redirect to="/"/> : this.addNotePage()
        );
    }

    countChars(event) {
        const value = event.target.value;
        this.setState({
            chars: value.length
        });
    }

    addNotePage() {
        const noteObj = {
            text: "",
            priority: "low"
        };
        return <div className="container">
            <form>
                <div className="form-group">
                    <label htmlFor="exampleFormControlSelect">Select priority:</label>
                    <select className="form-control"
                            id="exampleFormControlSelect"
                            onChange={event => noteObj.priority = event.target.value}>
                        <option>low</option>
                        <option>medium</option>
                        <option>high</option>
                    </select>
                </div>
                <div className="form-group">
                    <label htmlFor="exampleFormControlTextarea">Enter note text:</label>
                    <textarea className="form-control"
                              id="exampleFormControlTextarea"
                              rows="3"
                              onKeyUp={event => noteObj.text = event.target.value}
                              onChange={this.countChars.bind(this)}/>
                    <div>
                        Characters {this.state.chars} / 500.
                    </div>
                </div>
                <div>
                    <ul className="nav nav-pills">
                        <li className="nav-item mr-2">
                            <Link className="nav-link btn-success"
                                  to='/addNote'
                                  onClick={this.addNote.bind(this, noteObj)}>Save</Link>
                        </li>
                        <li className="nav-item mr-2">
                            <Link className="nav-link btn-danger" to='/'>Cancel</Link>
                        </li>
                    </ul>
                </div>
            </form>
        </div>;
    }
}
