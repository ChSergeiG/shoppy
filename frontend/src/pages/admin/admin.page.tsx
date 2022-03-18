import React, {Component} from "react";
import ButtonBar from "../../components/ButtonBar";
import {Link} from "react-router-dom";
import AuthorizationOverlay from "./components/AuthorizationOverlay";
import {Button} from "@mui/material";
import {ApplicationContext} from "../../applicationContext";

type AdminPageProps = {
    component: JSX.Element;
}

type AdminPageState = {
    authorized: boolean;
}

export class AdminPage extends Component<AdminPageProps, AdminPageState> {

    static contextType = ApplicationContext;
    // @ts-ignore
    context!: React.ContextType<typeof ApplicationContext>

    constructor(props: AdminPageProps) {
        super(props);
    }

    render() {
        const {authorized} = this.context;
        return authorized
            ? (<>
                <ButtonBar
                    items={[
                        {element: (<Button><Link to="/">Main</Link></Button>), adminButton: false},
                        {element: (<Button><Link to="/admin/accounts">Accounts</Link></Button>), adminButton: true},
                        {element: (<Button><Link to="/admin/goods">Goods</Link></Button>), adminButton: true},
                        {element: (<Button><Link to="/admin/orders">Orders</Link></Button>), adminButton: true}
                    ]}
                />
                {this.props.component}
            </>)
            : (<AuthorizationOverlay/>);
    }
}
