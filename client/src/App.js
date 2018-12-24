import React, { Component } from 'react';
import logo from './logo.svg';
import './App.css';

import { BrowserRouter as Router, Route, Link } from "react-router-dom";

import Home from "./components/pages/Home";
import Problems from "./components/pages/Problems";
import Profile from "./components/pages/Profile";
import Sessions from "./components/pages/Sessions";
import Signin from "./components/pages/Signin";
import Signup from "./components/pages/Signup";

class App extends Component {
  constructor(props){
    super(props);
    this.state = {
      isAutorizate: false,
      login: ''
    }
  }

  componentDidUpdate() {
    // console.log(sessionStorage)
  }

  handleLogOut = () => {
    // TO DO log out
  }

  setSessionStorage = (token, login) => {
    sessionStorage.setItem('token', token);
    console.log(sessionStorage.getItem('token'));
    this.setState({
      isAutorizate: true,
      login: login
    });
  }
  
  render() {
    return (
      <Router>
      <div>
        <header>
          <div className="logo">
            <Link to="/">CodingBattle</Link>
          </div>

          <nav>
            <Link to="/problems">Problems</Link>
            <Link to="/sessions">Sessions</Link>
          </nav>

          <div className="profile">
            { this.state.isAutorizate && [<Link to="/profile">{this.state.login}</Link>, <div className='btn -logout' onClick={this.handleLogOut}>Log out</div>]}
            { !this.state.isAutorizate && [<Link to="/signin">Sign in</Link>,<Link to="/signup">Sign up</Link>] }
            
          </div>
        </header>

        <main>
          <Route exact path="/" component={Home} />
          <Route path="/problems" component={Problems} />
          <Route path="/profile" component={Profile} />
          <Route path="/sessions" component={Sessions} />
          <Route path="/signin" render={()=><Signin setSessionStorage={this.setSessionStorage}/>} />
          <Route path="/signup" component={Signup} />
        </main>
      </div>
    </Router>
    );
  }
}

export default App;
