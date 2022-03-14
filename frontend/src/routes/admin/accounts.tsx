import React from "react";
import AccountsTable from "../../components/admin/accounts/AccountsTable";
import ButtonBar from "../../components/ButtonBar";
import {Link} from "react-router-dom";
import AuthorizationOverlay from "../../components/admin/AuthorizationOverlay";

export default function Accounts() {
    return (
        <>
            <ButtonBar items={[
                <Link to="/">Main</Link>,
                <Link to="/admin/accounts">Accounts</Link>,
                <Link to="/admin/goods">Goods</Link>,
                <Link to="/admin/orders">Orders</Link>
            ]}/>
            <AuthorizationOverlay/>
            <AccountsTable/>
        </>
    );
}