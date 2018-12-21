import React, { Component } from 'react';
import './Home.css';

class Signin extends Component {

  handleSubmit(event) {
    // TODO submit data
  }

  render() {
    return (
      <section>
        <h1>Sign in</h1> 

        <form onSubmit={this.handleSubmit} className="form">
          <label><span>Email:</span> <input type="text" value=""/></label>
          <label><span>Password:</span> <input type="password" value=""/></label>
          <input type="submit" value="Log in" />
        </form>
      </section>
    );
  }
}

export default Signin;