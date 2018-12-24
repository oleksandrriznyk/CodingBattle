import React, { Component } from 'react';
import { BrowserRouter as Router, Route, Link } from "react-router-dom";
import SockJS from "sockjs-client";
import { Stomp } from "@stomp/stompjs";

import './Sessions.css';
import Session from './Session';


class Sessions extends Component {
    constructor(props){
        super(props);
        this.match = props.match;
        this.state = {
            sessions: [],
            acceptedSessionId: '',
            token: sessionStorage.getItem('token')
        };
        this.createSession = this.createSession.bind(this);
    }

    componentDidMount() {
        this.getAllSessions();
    }

    getAllSessions = () => {
        fetch('/api/v1/sessions/all', {
            method: 'GET',
            headers: {'Authorization' : this.state.token}
            })
            .then(response => response.json())
            .then(data => {
                this.setState({sessions: data })
            })
            .catch(err => console.error(this.props.url, err.toString()));
    }

    renderLinks = () => {
        let links = [];
        console.log('Sessions = >' + this.state.sessions)
        for(let item of this.state.sessions) {
            let link = `${this.match.url}/${item.id}`;
            links.push(
                <li className="session">
                    <Link to={link}>{item.playerFirst.login}</Link>
                    <button className="button-accept" onClick={this.connect} value={item.id}>Accept</button>
                </li>
            )
        }
        return links;
    }

    createSession = () => {

        fetch('api/v1/sessions/prepareSession', {
            method: 'POST',
            headers: {'Authorization': this.state.token}
            })
            .then(response=>response.json())
            .then(data=> {
                console.log(data)
                this.setState({acceptedSessionId: data.id})
                console.log(this.state.acceptedSessionId)
            })
            .catch(err => console.error(err.toString()));
    }

    connect = (event) => {
        const sessionId = event.target.value;
        fetch(`api/v1/sessions/connect?sessionId=${sessionId.toString()}`, {
            method: 'GET',
            headers: {'Authorization': this.state.token},
        })
        .then(response=>response.json())
        .then(data=>console.log(data))
        .catch(err=>console.log(err.toString()));
    }

    render() {
        return (
            <section>
                <Route path={`${this.match.path}/:sessionId`} component={Session} />
                <Route
                    exact
                    path={this.match.path}
                    render={()=> <div>
                        <h1>Sessions
                            <button onClick={this.createSession}>Create Session</button></h1>
                        <ul className="session-list">
                            { this.renderLinks() }
                        </ul>
                    </div>}
                 />
             </section>
        );
    }
}

export default Sessions;