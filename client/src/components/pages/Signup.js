import React, { Component } from 'react';
import './Home.css';
import './Signup.css';

class Signup extends Component {
  constructor(props) {
    super(props);


    this.state = {
      message: '',
      login: '',
      email: '',
      password: '',
      repeatPassword: ''
    }
  }

  handleSubmit = (event) => {
    event.preventDefault();
    const data = new FormData(event.target);
    
    fetch('/api/v1/users/sign-up', {
      method: 'POST',
      body: JSON.stringify({
        login: this.state.login,
        email: this.state.email,
        password: this.state.password
       }),
      headers: {'Content-Type': 'application/json'}
    }).then(response=>response.json()
    ).then(data=>{
      this.updateDOM(data);
      console.log('Test', data);
      this.setState({message: data.message})
      this.showMessage(this.state.message);
    });
  }

  updateDOM = (data)=> {

    const input = document.getElementById('login');
    const help = document.getElementById('signUpHelp');
    if(data.httpStatus==="409"){
        input.classList.add("error");
        help.classList.add("help-error");
    } else {
        input.classList.add("success");
        help.classList.add("help-success");
    }
  }

  showMessage = (data) => {
        const obj = document.getElementById("signUpHelp");
        obj.innerHTML = data;
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
        <div>
          <label><span>Login:</span> <input value={this.state.login} onChange={this.handleLoginChange} id="login" name="login" type="text"/></label>

        </div>
        <div>
          <label><span>Email:</span> <input value={this.state.email} onChange={this.handleEmailChange} id="email" name="email" type="text"/></label>
        </div>
        <div>
          <label><span>Password:</span> <input value={this.state.password} onChange={this.handlePasswordChange} id="password" name="password" type="password"/></label>
        </div>
        <div>
          <label><span>Repeat password:</span> <input value={this.state.repeatPassword} onChange={this.handleRepeatPasswordChange} type="password"/></label>
        </div>
          <input type="submit" value="Sign up" />
          <small id="signUpHelp" class="form-text text-muted"></small>
        </form>
      </section>
    );
  }
}

export default Signup;