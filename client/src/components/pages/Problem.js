import React, { Component } from 'react';
import CodeFlask from 'codeflask';
import './Problem.css';

class Problem extends Component {
  constructor(props) {
    super(props);
    this.match = props.match;
    
    this.state = {
      flask: {},
      task: {},
      token: sessionStorage.getItem('token')
    }
  }

  componentDidMount() {

    this.setState({
      task: this.props.location.state.data.task
    }, this.codeFlaskInit)
    
    // debugger;
    // this.getTaskByID(taskId);
    // flask.addLanguage('java', options)

  }

  codeFlaskInit() {
    this.setState({
      flask: new CodeFlask('#code', { language: 'js', lineNumbers: true })
    })
  }

  getTaskByID = (taskId) => {
    fetch(`/api/v1/tasks/${taskId}`)
      .then(response => response.json())
      .then(data => {
        this.setState({task: data }, this.codeFlaskInit)
    })
    .catch(err => console.error(this.props.url, err.toString()));
  }

  handleSubmitCode = (event) => {
    const currentSessionId = this.props.location.state.sessionId;

    const code = this.state.flask.getCode();
    const methodName = this.state.task.methodName;
    console.log(currentSessionId);

    console.log(code)
    event.preventDefault();
    fetch('api/v1/compilation/compile', {
     method: 'POST',
     headers: {'Content-Type':'application/json', 'Authorization': this.state.token},
     body: JSON.stringify({
      source: code,
      gameName: methodName,
      taskId: this.state.task.id,
      sessionId: currentSessionId
     })
    }).then(function(response) {
      return response.json();
    }).then(function(data) {

      console.log('Test', data);
    });
  }
  
  render() {
    return (
      <section>
        <div className="problem-info">
          <h2>Task #{this.match.params.problemId}</h2>
          <p>{this.state.task.taskText}</p>
        </div>

        <div className="code" id="code">
          { `${this.state.task.startCode}` }       
        </div>

        <div className="problem-footer">
          <input className="problem-footer__submit" onClick={this.handleSubmitCode} type="button" value="Submit"/>
        </div>
      </section>
    );
  }
}

export default Problem;
