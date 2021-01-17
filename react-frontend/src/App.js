
import "bootstrap";
import 'bootstrap/dist/css/bootstrap.min.css';

import './App.css';
import { Component } from 'react';
import axios from 'axios';



class App extends Component {

  state = {
    algorithms: [],
    items: [],
    dataset: '',
    algo: '',
    output: ''
  }

  server = 'http://localhost:8080/api/algorithms/';

  componentDidMount() {
    const headers = {
      'Content-Type': 'text/plain'
    };
    axios.get(`${this.server}`, { headers })
      .then(res => {
        this.setState({ algorithms: res.data });
      })
  }

  getDataSet(changeEvent) {
    if (changeEvent.target.value !== 'base') {
      this.setState({
        algo: changeEvent.target.value
      }, () => {
        axios.get(`${this.server}${this.state.algo}/`)
          .then(res => {
            this.setState({
              items: res.data,
              dataset: "",
              output: ""
            });
          })
      });
    } else {
      this.setState({
        algo: '',
        items: [],
        dataset: "",
        output: ""
      });
    }
  }

  updateTextArea(changeEvent) {
    this.setState({
      dataset: changeEvent.target.value
    });
  }

  handleEdits(changeEvent) {
    this.setState({
      dataset: changeEvent.target.value
    });
  }

  submitAlgo(clickEvent) {
    clickEvent.preventDefault();
    if (this.state.algo !== "") {
      axios.post(`${this.server}`, {
        algorithmCode: this.state.algo,
        inputDataset: this.state.dataset
      })
        .then(res => {
          this.setState({ output: res.data });
        });
    } else {
      alert("Please select an algorithm and provide correct input.");
    }
  }

  render() {
    let { algorithms, items } = this.state;
    return (

      <div className="App">
        <nav className="navbar navbar-dark bg-dark">
          <div className="container">
            <a className="navbar-brand" href="/">
              <img src="./logo192.png" width="30" height="30" className="d-inline-block align-top mr-2" alt=""></img>
              DP Algorithms
            </a>
            <span className="navbar-text">
              Your classic dynamic programming application
            </span>
          </div>
        </nav>
        <div className="container mt-4">
          <div className="row">

            <form className="col-7 mt-4">
              <div className="form-group row">
                <label for="algo-name" className="col-sm-2 col-form-label">Algorithm</label>
                <select name="algo-name" id="algo-name" className="form-control col-sm-10" onChange={e => { this.getDataSet(e) }}>
                  <option className="slct" value="base">Please select an algorithm</option>
                  {algorithms.map(algorithm => (
                    <option id={algorithm.code} key={algorithm.code} value={algorithm.code}>{algorithm.name}</option>
                  ))}
                </select>
              </div>
              <div className="form-group row">
                <label for="algo-dataset" className="col-sm-2 col-form-label">Dataset</label>
                <select name="algo-dataset" id="algo-dataset" className="form-control col-sm-10" onChange={e => { this.updateTextArea(e) }}>
                  <option className="slct" value="base"  >Please select a dataset</option>
                  {items.map(item => (
                    <option value={item.content} key={item.code} >{item.code}</option>
                  ))}
                </select>
              </div>
              <div className="form-group row">
                <label for="input-text" className="col-sm-2 col-form-label">Input Dataset</label>
                <textarea class="form-control col-sm-10" rows={9} id="input-text" value={this.state.dataset} onChange={(e) => this.handleEdits(e)}></textarea>
              </div>
              <div className="row d-flex flex-row-reverse">
                <button type="button" onClick={(e) => this.submitAlgo(e)} className="btn btn-dark col-10 justify-content-right">Submit</button>
              </div>
            </form>
            <div className="col-5 mt-3">
              <div className="form-group">
                <label for="output-text">Algorithm Output</label>
                <textarea className="form-control" id="output-text" value={this.state.output} readOnly aria-label="With textarea" rows="15"></textarea>
              </div>
            </div>
          </div>
        </div>
        <footer className="footer bg-dark text-white border-top-2 border-dark">
          <div className="container pt-3">
            <div className="row">
              <div className="col">
                <p>Designed and developed by:</p>
                <ul>
                  <li>&emsp;Mehran Kamal&emsp;&nbsp;<a href="mailto:k181098@nu.edu.pk" aria-disabled="true">k181098@nu.edu.pk</a></li>
                  <li>&emsp;Taha Warsi&emsp;&emsp;&emsp;<a href="mailto:k181174@nu.edu.pk" aria-disabled="true">k181174@nu.edu.pk</a></li>
                </ul>
              </div>
            </div>
          </div>
        </footer>
      </div>
    );
  }
}

export default App;
