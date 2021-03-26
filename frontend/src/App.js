import React, {Component} from 'react'

import Home from './components/pages/MainPage'
import Admin from './components/pages/AdminPage'
import {Route, Switch} from "react-router";
import ButtonBar from "./components/ButtonBar";
import {Button, TableCell} from "@material-ui/core";
import {Link} from "react-router-dom";

class App extends Component {

    render() {
        let items = [(
            <TableCell style={{border: 'none'}}>
                <Link to="/">
                    <Button>Home</Button>
                </Link>
            </TableCell>
        ), (
            <TableCell style={{border: 'none'}}>
                < Link to="/admin">
                    <Button>Admin</Button>
                </Link>
            </TableCell>
        )]


        return (
            <div>
                <header>
                    {ButtonBar(items)}
                </header>
                <main>
                    <Switch>
                        <Route
                            path="/"
                            component={Home}
                            exact
                        />
                        <Route
                            path="/admin"
                            component={Admin}
                        />
                    </Switch>
                </main>
            </div>
        );
    }
}

export default App;
