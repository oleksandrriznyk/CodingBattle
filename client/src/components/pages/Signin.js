import React, { Component } from 'react';
import './Home.css';

class Signin extends Component {

    constructor(props){
        super(props);
        this.state = {
            login: '',
            password: '',
            authorizationToken: ''
        }
    }
  handleSubmit = (event) =>{
    event.preventDefault();
    const data = new FormData(event.target);

    fetch('/login', {
        method: 'POST',
        body: JSON.stringify({
            login: this.state.login,
            password: this.state.password
        })
    }).then(function(response){
        console.log(response.headers.get("authorization"));
    }).then(function(data){
        console.log('Test', data);
        debugger;
    })
  }

  handleLoginChange = (event) => {
    this.setState({login: event.target.value});
  }

  handlePasswordChange = (event) => {
    this.setState({password: event.target.value});
  }

  render() {
    return (
      <section>
        <h1>Sign in</h1> 

        <form onSubmit={this.handleSubmit} className="form">
          <label><span>Login:</span> <input type="text" value={this.state.login} onChange={this.handleLoginChange} id="login" name="login"/></label>
          <label><span>Password:</span> <input type="password" value={this.state.password} onChange={this.handlePasswordChange} id="password" name="password"/></label>
          <input type="submit" value="Log in" />
        </form>
      </section>
    );
  }
}

export default Signin;