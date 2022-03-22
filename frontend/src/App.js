import React, {Component} from 'react'
import ButtonBar from "./components/ButtonBar";
import {Link} from "react-router-dom";

class App extends Component {

    render() {
        return (
            <>
                <ButtonBar
                    items={[
                        {
                            element: (
                                <Link
                                    to="/admin/orders"
                                    style={{textDecoration: "none"}}
                                >
                                    🔑
                                </Link>
                            ), adminButton: true
                        }
                    ]}
                />
                <h2>Main Page</h2>
            </>
        );
    }
}

export default App;
