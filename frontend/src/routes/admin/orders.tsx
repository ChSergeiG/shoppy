import React from "react";
import OrdersTable from "../../components/admin/orders/OrdersTable";
import ButtonBar from "../../components/ButtonBar";
import {Link} from "react-router-dom";

export default function Orders() {

    return (
        <>
            <ButtonBar items={[
                <Link to="/">Main</Link>,
                <Link to="/admin/goods">Goods</Link>,
                <Link to="/admin/orders">Orders</Link>,
                <Link to="/admin/users">Users</Link>
            ]}/>
            <OrdersTable/>
        </>
    );

}
