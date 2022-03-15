import React, {Component} from 'react'
import ButtonBar from "./components/ButtonBar";
import {Link} from "react-router-dom";

class App extends Component {

    render() {
        return (
            <>
                <ButtonBar
                    items={[
                        <Link to="/admin/orders">{"\u26BF"}</Link>
                    ]}
                    authorized={false}
                />
                <h2>Main Page</h2>
            </>
        );
    }
}

export default App;
