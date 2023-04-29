import React, {Component} from 'react';
import axios from 'axios';

class TestPage extends Component {

  componentDidMount(){
    axios.get("/xx")
    .then( response => console.log(response.data.name))
  }

  render() {
    return (
      <h1> 안녕하냐 </h1>
    )
  }


}

export default TestPage;
