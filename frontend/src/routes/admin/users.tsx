import React from "react";
import UsersTable from "../../components/admin/users/UsersTable";
import ButtonBar from "../../components/ButtonBar";
import {Link} from "react-router-dom";

export default function Users() {
    return (
        <>
            <ButtonBar items={[
                <Link to="/">Main</Link>,
                <Link to="/admin/goods">Goods</Link>,
                <Link to="/admin/orders">Orders</Link>,
                <Link to="/admin/users">Users</Link>
            ]}/>
            <UsersTable/>
        </>
    );
}