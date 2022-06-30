import React from "react"
import './App.css';
import Todo from './Todo';
import AddTodo from"./AddTodo"
import {Paper, List, Container} from "@material-ui/core"

class App extends React.Component {
    constructor(props) {
        super(props);
        this.state ={
            items: [],
        }
    };
    componentDidMount() {
        const requestOptions={
            method: "GET",
            headers: {"Content-Type": "application/json"}
        };

        fetch("http://localhost:8080/todo",requestOptions)
            .then((response)=>response.json())
            .then(
                (response) =>{
                    this.setState({
                        items: response.data
                    });
                },
                (error) => {
                    this.setState({
                        error,
                    });
                }
            );
    }

    add = (item) => {
        const thisItems = this.state.items;
        item.id = "ID-"+thisItems.length;
        item.done = false;
        thisItems.push(item);
        this.setState({items:thisItems});
        console.log("items : ", this.state.items);
    }

    delete = (item) =>{
        const thisItems = this.state.items;
        console.log("Before update items: ",thisItems);
        const newItems = thisItems.filter(e=> e.id!== item.id);
        this.setState({items: newItems},() =>{
            console.log("update items: ",this.state.items)
        });
    }

    render() {
        const todoItems = this.state.items.length > 0 && (
            <Paper style={{margin:16}}>
                <List>
                    {this.state.items.map((item,idx) =>(
                        <Todo item={item} key={item.id} delete={this.delete}/>
                    ))}
                </List>
            </Paper>
        );

        return (
            <div className="App">
                <Container maxWidth="md">
                    <AddTodo add={this.add}/>
                    <div className="TodoList">{todoItems}</div>
                </Container>
            </div>
        );
    }
}

export default App;
