import React, { Component } from 'react';
import { BrowserRouter as Router, Route, Link } from "react-router-dom";

import Problem from './Problem';
import './Problems.css';

class Problems extends Component {
  constructor(props) {
    super(props);
    this.match = props.match;
  }
  
  render() {
    return (
      <section>
          <Route path={`${this.match.path}/:problemId`} component={Problem} />
          <Route
            exact
            path={this.match.path}
            render={() => <div>
              <h1>Problems</h1> 
              <ul className="problems-list">
                <li>
                  <Link to={`${this.match.url}/1`}>First task</Link>
                </li>
                <li>
                  <Link to={`${this.match.url}/2`}>Second task</Link>
                </li>
                <li>
                  <Link to={`${this.match.url}/3`}>Bubble sort</Link>
                </li>
              </ul>
            </div>}
          />
      </section>
    );
  }
}

export default Problems;
