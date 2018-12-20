import React, { Component } from 'react';
import { BrowserRouter as Router, Route, Link } from "react-router-dom";

class Session extends Component {
    constructor(props){
        super(props);
        this.match = props.match;
        }

    render(){
        return (
            <div>
                Session
            </div>);
         }
    }

export default Session;