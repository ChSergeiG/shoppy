import React, {Component} from "react";
import ButtonBar from "../../components/ButtonBar";
import {Link} from "react-router-dom";
import AuthorizationOverlay from "./components/AuthorizationOverlay";
import {Button} from "@mui/material";
import {STORED_JWT_TOKEN_VALIDITY_KEY} from "../../utils/API";

type AdminPageState = {
    authorized: boolean;
}

type AdminPageProps = {
    component: JSX.Element;
}

export class AdminPage extends Component<AdminPageProps, AdminPageState> {

    constructor(props: AdminPageProps) {
        super(props);
        this.state = {
            authorized: false
        }
        setInterval(this.checkAuthorization, 5_000);
    }

    componentDidMount() {
        this.checkAuthorization()
    }

    checkAuthorization = () => {
        this.setState({
            ...this.state,
            authorized: localStorage.getItem(STORED_JWT_TOKEN_VALIDITY_KEY) === "VALID"
        });
    }

    render() {
        const {authorized} = this.state;
        return (
            <>
                <ButtonBar
                    items={[
                        <Button><Link to="/">Main</Link></Button>,
                        <Button><Link to="/admin/accounts">Accounts</Link></Button>,
                        <Button><Link to="/admin/goods">Goods</Link></Button>,
                        <Button><Link to="/admin/orders">Orders</Link></Button>
                    ]}
                    authorized={authorized}
                />
                {authorized
                    ? this.props.component
                    : <AuthorizationOverlay
                        onClose={() => console.log('closed')}
                    />}
            </>
        );
    }
}
