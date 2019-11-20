import React, {Component} from "react";
import {Link} from "react-router-dom";


export class NotFound extends Component {
    render() {
        return (
            <div>
                <br/>
                <br/>
                <ul className="nav flex-column justify-content-center">
                    <li className="nav-item">
                        You are lost, buddy! That's a 404 page
                    </li>
                    <li className="nav-item">
                        <Link to='/'>Main page</Link>
                    </li>
                </ul>
            </div>
        )
    }
}