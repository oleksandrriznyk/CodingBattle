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
            headers: {'Authorization' : 'Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ6eW1hcmV2MiIsImV4cCI6MTU0NTMyNjA4OX0.09VJeWQyk8LA2i2oR-y7npa1_BW6A3k2Y2KPCDh_II9tUOIB3pYA7DB3w2URbnENnjKshFPQzr7FF-zp6bwC0A'}
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
            headers: {'Authorization': 'Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ6eW1hcmV2MiIsImV4cCI6MTU0NTMyNjA4OX0.09VJeWQyk8LA2i2oR-y7npa1_BW6A3k2Y2KPCDh_II9tUOIB3pYA7DB3w2URbnENnjKshFPQzr7FF-zp6bwC0A'}
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
        var socket = new SockJS('http://localhost:8080/api/v1/sessions/gs-codingbattle');
        console.log("socket=>" + socket);
        var stompClient = Stomp.over(socket);
        stompClient.connect({}, function(frame) {
            console.log('Connected: ' + frame);
            stompClient.subscribe(`/session/${this.state.acceptedSessionId}`, function(message) {
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