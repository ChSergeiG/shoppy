import React from "react";
import {Link} from "react-router-dom";
import GoodsTable from "../../components/admin/goods/GoodsTable";
import ButtonBar from "../../components/ButtonBar";

export default function Goods() {
    return (
        <>
            <ButtonBar items={[
                <Link to="/">Main</Link>,
                <Link to="/admin/goods">Goods</Link>,
                <Link to="/admin/orders">Orders</Link>,
                <Link to="/admin/users">Users</Link>
            ]}/>
            <GoodsTable/>
        </>
    );
}
