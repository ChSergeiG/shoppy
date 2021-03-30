import React, {Component} from 'react'

import Home from './components/pages/MainPage'
import Admin from './components/pages/AdminPage'
import {Route, Switch} from "react-router";
import {Button, ButtonGroup} from "@material-ui/core";
import {Link} from "react-router-dom";

class App extends Component {

    render() {
        return (
            <div>
                <header>
                    <ButtonGroup>
                        <Link to="/">
                            <Button>Home</Button>
                        </Link>
                        <Link to="/admin">
                            <Button>{"\u26BF"}</Button>
                        </Link>
                    </ButtonGroup>
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
