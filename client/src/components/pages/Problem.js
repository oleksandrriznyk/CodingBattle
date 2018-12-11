import React, { Component } from 'react';
import CodeFlask from 'codeflask';
import './Problem.css';

class Problem extends Component {
  constructor(props) {
    super(props);
    this.match = props.match;
    
    this.state = {
      flask: {}
    }
  }

  componentDidMount() {
    this.setState({
      flask: new CodeFlask('#code', { language: 'js', lineNumbers: true })
    })
    // flask.addLanguage('java', options)
  }

  handleSubmitCode = () => {
    const code = this.state.flask.getCode();
    alert(code);
  }
  
  render() {
    return (
      <section>
        <div className="problem-info">
          <h2>Task #{this.match.params.problemId}</h2>
        </div>

        <div className="code" id="code">
        {`
public static void main(String[] args){
  for(int i = -5; i < 33; i++){
      System.out.println(i + ": " + toBinary(i));
      System.out.println(i);
      //always another way
      System.out.println(i + ": " + Integer.toBinaryString(i));
  }
}
        `.trim()}       
        </div>

        <div className="problem-footer">
          <input className="problem-footer__submit" onClick={this.handleSubmitCode} type="button" value="Submit"/>
        </div>
      </section>
    );
  }
}

export default Problem;
