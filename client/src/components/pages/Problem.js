import React, { Component } from 'react';
import CodeFlask from 'codeflask';
import './Problem.css';

class Problem extends Component {
  constructor(props) {
    super(props);
    this.match = props.match;
    
    this.state = {
      flask: {},
      task: {}
    }
  }

  componentDidMount() {
    this.getTaskByID();
    // flask.addLanguage('java', options)
  }

  codeFlaskInit() {
    this.setState({
      flask: new CodeFlask('#code', { language: 'js', lineNumbers: true })
    })
  }

  getTaskByID = () => {
    fetch(`/api/v1/tasks/${this.match.params.problemId}`)
      .then(response => response.json())
      .then(data => {
        this.setState({task: data }, this.codeFlaskInit)
    })
    .catch(err => console.error(this.props.url, err.toString()));
  }

  handleSubmitCode = (event) => {
    const code = this.state.flask.getCode();

    console.log(code)
    
    event.preventDefault();
    fetch('http://localhost:8080/api/v1/compilation/compile', {
     method: 'post',
     headers: {'Content-Type':'application/json'},
     body: {
      "source": "public class Temp{ public int arraySum(int[] arr){ int sum=0;for(int i=0;i<arr.length;i++){ sum+=arr[i];}return sum;}}",
      "gameName": "Temp",
      "taskId": this.match.params.problemId
     }
    }).then(function(response) {
      return response.json();
    }).then(function(data) {

      console.log('Test', data);
      debugger;
    });
  }
  
  render() {
    return (
      <section>
        <div className="problem-info">
          <h2>Task #{this.match.params.problemId}</h2>
          <p>{this.state.task.taskText}</p>
          <p>{this.state.task.inputType}</p>
        </div>

        <div className="code" id="code">
          {this.state.task.startCode}       
        </div>

        <div className="problem-footer">
          <input className="problem-footer__submit" onClick={this.handleSubmitCode} type="button" value="Submit"/>
        </div>
      </section>
    );
  }
}

export default Problem;
