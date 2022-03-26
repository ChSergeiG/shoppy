import React, {useState} from "react";
import ButtonBar from "../components/ButtonBar";
import {Link} from "react-router-dom";
import {Paper} from "@mui/material";
import GoodCard from "../components/GoodCard";

const MainPage: React.FC = () => {

    const [state, setState] = useState({});

    return (
        <div>
            <ButtonBar
                items={[{
                    element: (<Link to="/admin/accounts" style={{textDecoration: "none"}}>🔑</Link>),
                    adminButton: true
                }]}
            />
            <Paper>
                <GoodCard
                    good={{
                        id: 1,
                        name: "well",
                        price: 17.4,
                        article: "asd-4445-ol",
                        status: "ACTIVE"
                    }}
                />
            </Paper>
        </div>
    );
}

export default MainPage;