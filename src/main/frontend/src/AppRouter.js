import React from "react"
import {Box, Typography} from "@material-ui/core";
import {Route, BrowserRouter as Router, Routes} from "react-router-dom";
import Login from "./Login";
import App from "./App";

function Copyright() {
    return(
        <Typography variant="body2" color={"textSecondary"} align={"center"}>
            {"Copyright "}
            fsoftwareengineer, {new Date().getFullYear()}
            {"."}
        </Typography>
    );
}

class AppRouter extends React.Component {
    render() {
        return(
            <div>
                <Router>
                    <div>
                        <Routes>
                            <Route path ="/login" element={<Login/>}>
                            </Route>
                            <Route path ="/" element={<App/>}>
                            </Route>
                        </Routes>
                    </div>
                    <Box mt={5}>
                        <Copyright/>
                    </Box>
                </Router>
            </div>
        );
    }
}

export default AppRouter;