import React, { Component } from 'react';
import './Home.css';

class Signup extends Component {
  constructor(props) {
    super(props);

    this.state = {
      login: '',
      email: '',
      password: '',
      repeatPassword: ''
    }
  }

  handleSubmit = (event) => {
    event.preventDefault();
    const data = new FormData(event.target);
    
    fetch('/api/v1/users', {
      method: 'POST',
      body: JSON.stringify({
        login: this.state.login,
        email: this.state.email,
        password: this.state.password
       })
    }).then(function(response) {
      return response.json();
    }).then(function(data) {

      console.log('Test', data);
      debugger;
    });
  }

  handleLoginChange = (event) => {
    this.setState({login: event.target.value});
  }

  handleEmailChange = (event) => {
    this.setState({email: event.target.value});
  }

  handlePasswordChange = (event) => {
    this.setState({password: event.target.value});
  }

  handleRepeatPasswordChange = (event) => {
    this.setState({repeatPassword: event.target.value});
  }

  render() {
    return (
      <section>
        <h1>Sign up</h1> 

        <form onSubmit={this.handleSubmit} className="form">
          <label><span>Login:</span> <input value={this.state.login} onChange={this.handleLoginChange} id="login" name="login" type="text"/></label>
          <label><span>Email:</span> <input value={this.state.email} onChange={this.handleEmailChange} id="email" name="email" type="text"/></label>
          <label><span>Password:</span> <input value={this.state.password} onChange={this.handlePasswordChange} id="password" name="password" type="password"/></label>
          <label><span>Repeat password:</span> <input value={this.state.repeatPassword} onChange={this.handleRepeatPasswordChange} type="password"/></label>
          <input type="submit" value="Sign up" />
        </form>
      </section>
    );
  }
}

export default Signup;