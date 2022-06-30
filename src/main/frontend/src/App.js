import logo from './logo.svg';
import React from "react"
import './App.css';
import Todo from './Todo';

class App extends React.Component {
    constructor(props) {
        super(props);
        this.state ={
            items: [
                {id: "0", title: "Hello World 1", done: true},
                {id: "1", title: "Hello World 2", done: false},
            ],
        }
    };

    render() {
        const todoItems = this.state.items.map((item,idx) =>(
            <Todo item={item} key={item.id}/>
        ));
        return <div className={App}>{todoItems}</div>;
    }
}

export default App;
