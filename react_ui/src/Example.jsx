import React from "react";

class LeftChild extends React.Component {
  constructor(props) {
    super(props);
  }

  render() {
    return (
      <div>
        <h1>Current Counter: {this.props.counter}</h1>
      </div>
    );
  }
}

function RightChild(props) {
  const idOdd = props.counter % 2 === 1;
  return (
    <div>
      <h1>Is Current Counter Odd: {idOdd ? "true" : "false"}</h1>
    </div>
  );
}

class Example extends React.Component {
  constructor() {
    super();
    this.state = {
      counter: 0,
    };
  }

  render() {
    return (
      <div>
        <div>
          <button
            onClick={() => this.setState({ counter: this.state.counter + 1 })}
          >
            Increment
          </button>
          <button
            onClick={() => this.setState({ counter: this.state.counter - 1 })}
          >
            Decrement
          </button>
        </div>
        <div>
          <LeftChild counter={this.state.counter} />
          <RightChild counter={this.state.counter} />
        </div>
      </div>
    );
  }
}

export default Example;
