import React, {Component} from 'react';
import '../styles/App.css';
import {NoteList} from "./NoteList";
import * as ReactRouterDOM from "react-router-dom";
import {NotFound} from "./NotFound";
import {AddNote} from "./AddNote";
import {ShowNote} from "./ShowNote";

class App extends Component {

    render() {
        const Route = ReactRouterDOM.Route;
        const Switch = ReactRouterDOM.Switch;
        const Router = ReactRouterDOM.BrowserRouter;
        return (
            <div className="container">
                <Router>
                    <Switch>
                        <Route exact path="/" component={NoteList}/>
                        <Route exact path="/showNote/:number" component={ShowNote}/>
                        <Route exact path="/addNote" component={AddNote}/>
                        <Route component={NotFound}/>
                    </Switch>
                </Router>
            </div>
        );
    }
}

export default App;
