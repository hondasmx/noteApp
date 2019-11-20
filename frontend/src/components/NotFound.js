import React, {Component} from "react";
import {Link} from "react-router-dom";


export class NotFound extends Component {
    render() {
        return (
            <div>
                You are lost, buddy!
                <br/>
                <br/>
                <Link to="/">Back</Link>
            </div>
        )
    }
}