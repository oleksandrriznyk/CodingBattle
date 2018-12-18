import React, { Component } from 'react';
import CodeFlask from 'codeflask';
import './Problem.css';

import Modal from 'react-modal';

class Problem extends Component {
  constructor(props) {
    super(props);
    this.match = props.match;
    
    this.state = {
      flask: {},
      task: {},
      tests: [],
      showModalWait: true,
      showModalResult: true,
      result: 'Ты победил'
    }
  }

  handleOpenModalWait = () => {
    this.setState({ showModalWait: true });
  }

  handleCloseModalWait = () => {
    this.setState({ showModalWait: false });
  }

  handleOpenModalResult = () => {
    this.setState({ showModalResult: true });
  }

  handleCloseModalResult = () => {
    this.setState({ showModalResult: false });
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
    const methodName = this.state.task.methodName;

    console.log(code)
    event.preventDefault();
    fetch('http://localhost:8080/api/v1/compilation/compile', {
     method: 'post',
     headers: {'Content-Type':'application/json'},
     body: JSON.stringify({
      source: code,
      gameName: methodName,
      taskId: this.match.params.problemId.toString()
     })
    }).then(function(response) {
      return response.json();
    }).then( (data) => {
      this.setState({
        tests: data.testResultList
      })
    });
  }

  testsRender() {
    let results = [];

    if(this.state.tests) {
      for(let item of this.state.tests) {
        results.push(
          <div className="test-result">
            { item.passed ? 'OK' : 'Wrong'}
          </div>
        );
      }
    } else {
      results.push(
        <div className="test-result">
          WRONG
        </div>
      );
    }

    return results;
  }
  
  render() {
    var modalStyles = {overlay: {zIndex: 10}};

    return (
      <section>
        <Modal isOpen={this.state.showModalWait} style={modalStyles} className="modal modal--wait">
            <button className="modal__close" onClick={this.handleCloseModalWait}>X потом скрыть эту кнопку</button>
            <div className="modal__content">
              <p>Ожидание соперника</p>
              <img src="https://loading.io/spinners/balls/lg.circle-slack-loading-icon.gif"/>
            </div>
        </Modal>

        <Modal isOpen={this.state.showModalResult} style={modalStyles} className="modal modal--result">
            <button className="modal__close" onClick={this.handleCloseModalResult}>Close</button>
            <div className="modal__content">
              <p>Результат</p>
              <div className="modal__result">{this.state.result}</div>
            </div>
        </Modal>
        
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

        <div className="problem-tests">
          { this.testsRender() }
        </div>
      </section>
    );
  }
}

export default Problem;
