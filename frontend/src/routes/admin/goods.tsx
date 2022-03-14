import React from "react";
import {Link} from "react-router-dom";
import GoodsTable from "../../components/admin/goods/GoodsTable";
import ButtonBar from "../../components/ButtonBar";
import AuthorizationOverlay from "../../components/admin/AuthorizationOverlay";

export default function Goods() {
    return (
        <>
            <ButtonBar items={[
                <Link to="/">Main</Link>,
                <Link to="/admin/accounts">Accounts</Link>,
                <Link to="/admin/goods">Goods</Link>,
                <Link to="/admin/orders">Orders</Link>
            ]}/>
            <AuthorizationOverlay/>
            <GoodsTable/>
        </>
    );
}
