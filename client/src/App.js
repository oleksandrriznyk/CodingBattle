import React, { Component } from 'react';
import logo from './logo.svg';
import './App.css';

import { BrowserRouter as Router, Route, Link } from "react-router-dom";

import Home from "./components/pages/Home";
import Problems from "./components/pages/Problems";
import Profile from "./components/pages/Profile";
import Signin from "./components/pages/Signin";
import Signup from "./components/pages/Signup";
import Sessions from "./components/pages/Sessions";

class App extends Component {
  render() {
    return (
      <Router>
      <div>
        <header>
          <div className="logo">
            <Link to="/">CodingBattle</Link>
          </div>

          <nav>
            <ul>
                <li>
                    <Link to="/problems">Problems</Link>
                </li>
                <li>
                    <Link to="/sessions">Sessions</Link>
                </li>
            </ul>

          </nav>

          <div className="profile">
            <Link to="/profile">Profile</Link>
            <Link to="/signin">Sign in</Link>
            <Link to="/signup">Sign up</Link>
          </div>
        </header>

        <main>
          <Route exact path="/" component={Home} />
          <Route path="/problems" component={Problems} />
          <Route path="/profile" component={Profile} />
          <Route path="/signin" component={Signin} />
          <Route path="/signup" component={Signup} />
          <Route path="/sessions" component={Sessions} />
        </main>
      </div>
    </Router>
    );
  }
}

export default App;
