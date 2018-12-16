import React, { Component } from 'react';
import { BrowserRouter as Router, Route, Link } from "react-router-dom";

import Problem from './Problem';
import './Problems.css';

class Problems extends Component {
  constructor(props) {
    super(props);
    this.match = props.match;
    this.state = {
      tasks: []
    }
  }

  componentDidMount() {
    this.getAllTasks();
  }

  getAllTasks = () => {
    fetch('/api/v1/tasks/all')
      .then(response => response.json())
      .then(data => {
        this.setState({tasks: data })
    })
    .catch(err => console.error(this.props.url, err.toString()));
  }

  renderLinks = () => {
    let links = [];
    for(let item of this.state.tasks) {
      let link = `${this.match.url}/${item.id}`;
      debugger;
      links.push(
        <li className="task">
          <Link to={link}>{item.taskText}</Link>
        </li>
      )
    }
    return links;
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
                { this.renderLinks() }
              </ul>
            </div>}
          />
      </section>
    );
  }
}

export default Problems;
