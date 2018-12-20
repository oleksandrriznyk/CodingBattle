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
            acceptedSessionId: ''
        };
        this.createSession = this.createSession.bind(this);
    }

    componentDidMount() {
        this.getAllSessions();
    }

    getAllSessions = () => {
        fetch('/api/v1/sessions/all', {
            method: 'GET',
            headers: {'Authorization' : 'Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ6eW1hcmV2MyIsImV4cCI6MTU0NTMzODMxM30.5-h_UQE-ZfMMU6xCr3Tvq_dCgYTbHBb3t8GaWQhH_00wFVAngIWF3BqrXAPanzrkZUMJv4AGOOZ9-uqWxymHKw'}
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
                    <button className="button-accept" onClick={this.connect}>Accept</button>
                </li>
            )
        }
        return links;
    }

    createSession = () => {

        fetch('api/v1/sessions/prepareSession', {
            method: 'POST',
            headers: {'Authorization': 'Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ6eW1hcmV2MyIsImV4cCI6MTU0NTMzODMxM30.5-h_UQE-ZfMMU6xCr3Tvq_dCgYTbHBb3t8GaWQhH_00wFVAngIWF3BqrXAPanzrkZUMJv4AGOOZ9-uqWxymHKw'}
            })
            .then(response=>response.json())
            .then(data=> {
                this.setState({acceptedSessionId: data.id})
                console.log(this.state.acceptedSessionId)
            })
            .catch(err => console.error(err.toString()));
            this.connect();
    }

    connect = () => {
        var socket = new SockJS('/api/v1/sessions/gs-codingbattle');
        var stompClient = Stomp.over(socket);
        stompClient.connect({}, function(frame) {
            console.log('Connected: ' + frame);
            stompClient.subscribe('/topic/messages', function(message) {
                console.log(JSON.parse(message.body));
            });
        });

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