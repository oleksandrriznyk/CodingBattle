import React, { Component } from 'react';

class Profile extends Component {
  componentDidMount() {
    fetch('http://localhost:8080/api/v1/users/ThundeRxD')
      .then(response => response.json())
      .then(data => {
        this.setState({data: data })
    })
      .catch(err => console.error(this.props.url, err.toString()))
  } 


  render() {
    return (
      <div>
        Profile
      </div>
    );
  }
}

export default Profile;