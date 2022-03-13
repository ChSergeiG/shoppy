import React from "react";
import AccountsTable from "../../components/admin/accounts/AccountsTable";
import ButtonBar from "../../components/ButtonBar";
import {Link} from "react-router-dom";

export default function Accounts() {
    return (
        <>
            <ButtonBar items={[
                <Link to="/">Main</Link>,
                <Link to="/admin/accounts">Accounts</Link>,
                <Link to="/admin/goods">Goods</Link>,
                <Link to="/admin/orders">Orders</Link>
            ]}/>
            <AccountsTable/>
        </>
    );
}