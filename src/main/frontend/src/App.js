import React from "react"
import './App.css';
import Todo from './Todo';
import AddTodo from"./AddTodo"
import {Paper, List, Container, AppBar, Grid, Typography, Button, Toolbar} from "@material-ui/core"
import {call,signout} from "./service/ApiService";

class App extends React.Component {
    constructor(props) {
        super(props);
        this.state ={
            items: [],
            loading: true
        }
    };
    componentDidMount() {
        call("/todo","GET",null)
            .then((response) => this.setState({items: response.data, loading:false})
            );
    }

    add = (item) => {
        call("/todo", "POST", item)
            .then((response) => this.setState({items: response.data})
            );
    }

    delete = (item) =>{
        call("/todo", "DELETE", item)
            .then((response) => this.setState({items: response.data})
            );
    }

    update = (item) =>{
        call("/todo", "PUT", item)
            .then((response) => this.setState({items: response.data})
            );
    }

    render() {
        const todoItems = this.state.items.length > 0 && (
            <Paper style={{margin:16}}>
                <List>
                    {this.state.items.map((item,idx) =>(
                        <Todo
                            item={item}
                            key={item.id}
                            delete={this.delete}
                            update={this.update}
                        />
                    ))}
                </List>
            </Paper>
        );

        const navigationBar = (
            <AppBar position={"static"}>
                <Toolbar>
                    <Grid justifyContent={"space-between"} container>
                        <Grid item>
                            <Typography variant={"h6"}>오늘의 할일</Typography>
                        </Grid>
                        <Grid>
                            <Button color={"inherit"} onClick={signout}>
                                로그아웃
                            </Button>
                        </Grid>
                    </Grid>
                </Toolbar>
            </AppBar>
        );

        const todoListPage = (
            <div>
                {navigationBar}
                <Container maxWidth="md">
                    <AddTodo add={this.add}/>
                    <div className="TodoList">{todoItems}</div>
                </Container>
            </div>
        );

        const loadingPage = <h1>로딩중 . . .</h1>

        let content = loadingPage;
        if (!this.state.loading) {
            content=todoListPage;
        }


        return (
            <div className="App">{content}</div>
        );
    }
}

export default App;


/*function exampleFunction() {
    return new Promise((resolve, reject) => {
        let oReq= new XMLHttpRequest();
        oReq.open("GET","http://localhost:8080/todo");
        oReq.onload =function (){   //resolve 상태
            resolve(oReq.response);
        };
        oReq.onerror = function () {//reject 상태
            reject(oReq.response);
        };
        oReq.send();                //pending 상태
    });
}

exampleFunction()
    .then((r)=>console.log("Resolved" + r))
    .catch((e)=>console.log("Rejected" + e))*/

/*fetch("localhost:8080/todo")
    .then(response=>{
        //response 수신 시 필요한 작업 작성
    })
    .catch(e=>{
        //에러 났을 시 처리
    })*/