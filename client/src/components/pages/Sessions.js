import React, { Component } from 'react';
import { BrowserRouter as Router, Route, Link } from "react-router-dom";
import SockJS from "sockjs-client";
import { Stomp } from "@stomp/stompjs";

import './Sessions.css';
import Session from './Session';

import { Redirect } from 'react-router-dom'

class Sessions extends Component {
    constructor(props){
        super(props);
        this.match = props.match;
        this.state = {
            sessions: [],
            acceptedSessionId: '',
            token: sessionStorage.getItem('token'),
            showWait: false,
            redirect: false,
            taskData: {}
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
                    <Link className="session__link" to={link}>{item.playerFirst.login}</Link>
                    <button className="session__button" onClick={this.connect} value={item.id}>Accept</button>
                </li>
            )
        }
        return links;
    }

    renderRedirect = () => {
        if (this.state.redirect) {
            return <Redirect to={{ pathname: '/problems/1', state: { data: this.state.taskData, sessionId: this.state.acceptedSessionId} }} />
        }
    }

    componentDidCatch = () => {
        window.location.href = "/signin";
    }

    createSession = () => {
        this.setState({ showWait: true});

        fetch('api/v1/sessions/prepareSession', {
            method: 'POST',
            headers: {'Authorization': this.state.token}
            })
            .then(response=>response.json())
            .then(data=> {
                this.setState({ showWait: false});
                console.log(data)
                this.setState({
                    acceptedSessionId: data.id,
                    redirect: true,
                    taskData: data
                });
                console.log(this.state.acceptedSessionId);
            })
            .catch(err => console.error(err.toString()));
    }

    rejectCreation = () => {
        this.setState({ showWait: false});
    }

    connect = (event) => {
        const sessionId = event.target.value;
        fetch(`api/v1/sessions/connect?sessionId=${sessionId.toString()}`, {
            method: 'GET',
            headers: {'Authorization': this.state.token},
        })
        .then(response=>response.json())
        .then(data=>{
            this.setState({
                acceptedSessionId: data.id,
                redirect: true,
                taskData: data
            })
        })
        .catch(err=>console.log(err.toString()));
    }

    render() {
        return (
            <section>
                {this.renderRedirect()}
                <Route path={`${this.match.path}/:sessionId`} component={Session} />
                <Route
                    exact
                    path={this.match.path}
                    render={()=> <div>
                        { this.state.showWait && <div className="wait">Ожидание соперника <div className="wait__button" onClick={this.rejectCreation}>Cancel</div></div>}
                        <h1>Sessions
                            <button className="btn -create-session" onClick={this.createSession}>Create session</button></h1>
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