import React, { Component } from 'react';
import CodeFlask from 'codeflask';
import './Problem.css';

let time = 0;
let interval = {};

class Problem extends Component {
  constructor(props) {
    super(props);
    this.match = props.match;
    
    this.state = {
      flask: {},
      task: {},
      token: sessionStorage.getItem('token'),
      time: 0,
      tests: {},
      endGame: false,
      winner: ''
    }
  }

  componentDidMount() {
    this.setState({
      task: this.props.location.state.data.task
    }, this.codeFlaskInit)
    
    interval = setInterval(() => {
      time += 1;
    }, 1000);

    setTimeout( ()=> {
      this.endGame();
    }, 1000 * 60 * 10);
    // debugger;
    // this.getTaskByID(taskId);
    // flask.addLanguage('java', options)
  }

  endGame = () => {
    this.setState({
      endGame: true
    })
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

    console.log(code);
    const that = this;

    event.preventDefault();
    fetch('api/v1/compilation/compile', {
     method: 'POST',
     headers: {'Content-Type':'application/json', 'Authorization': this.state.token},
     body: JSON.stringify({
      source: code,
      gameName: methodName,
      taskId: this.state.task.id,
      sessionId: currentSessionId,
      time: time
     })
    }).then((response) => {
      return response.json();
    }).then( (data) => {
      console.log('Test', data);
      that.setState({
        tests: data
      }, this.checkResults);
    });
  }

  checkResults = () => {
      let error = false;
  
      if(this.state.tests.testResultList) {
        for (let item of this.state.tests.testResultList ) {
          if(item.passed !== true) {
            error = true;
          }
        }

        if(error === false) {
          clearInterval(interval);
          this.setState({
            endGame: true,
            time: time
          }, this.submitResults)
        }
      } 
  }

  renderTests = () => {
    let testsArr = [];

    if(this.state.tests.testResultList) {
      for (let item of this.state.tests.testResultList ) {
        testsArr.push(<div className="test">
          <pre>{
            `actualResults: ${item.actualResults}
id: ${ item.id }
inputParams: ${ item.inputParams }
outputParams: ${ item.outputParams }
passed: ${ item.passed } `

          }
          </pre>
        </div>)
      }
    } else {
      testsArr.push(<div>No test results</div>);
    }

    return testsArr;
  }

  // Выполняется если все тесты зашли
  submitResults = () => {
    const that = this;
    const currentSessionId = this.props.location.state.sessionId;

    fetch(`/api/v1/sessions/${currentSessionId}/submit`, {
      method: 'GET',
      headers: {'Content-Type':'application/json', 'Authorization': this.state.token},
     })
     .then(response => response.json())
     .then( (data) => {
                console.log(data);
      // получаю имя победителя
        
       that.setState({
         winner: data.winnerLogin
       });

     }
    );
  }

  endGameWindow = () => {
    return <div className="wait">
      Соревнование закончилось
      { this.state.winner ? <div>Ожидание соперника</div> : <div>Победил {this.state.winner}</div>}
    </div>
  }
  
  render() {
    return (
      <section>
        {this.state.endGame /*&& this.endGameWindow()*/}

        <div className="problem-info">
          <h2>Task #{this.match.params.problemId}</h2>
          <p>{this.state.task.taskText}</p>
        </div>

        <div className="code" id="code">
          { this.state.task.startCode }       
        </div>

        <div className="problem-footer">
          <input className="problem-footer__submit" onClick={this.handleSubmitCode} type="button" value="Submit"/>
        </div>

        <div className="problem-tests">
          { this.renderTests() }
        </div>
      </section>
    );
  }
}

export default Problem;
