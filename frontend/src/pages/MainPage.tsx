import React from "react";
import ButtonBar from "../components/ButtonBar";
import {Link} from "react-router-dom";

const MainPage: React.FC = () => {


    return (
        <div>
            <ButtonBar
                items={[{
                    element: (<Link to="/admin/accounts" style={{textDecoration: "none"}}>🔑</Link>),
                    adminButton: true
                }]}
            />
            <h2>Main Page</h2>
        </div>
    );
}

export default MainPage;