import React, {PropsWithChildren, useContext} from "react";
import ButtonBar from "../../components/ButtonBar";
import {Link, useParams} from "react-router-dom";
import AuthorizationOverlay from "./components/AuthorizationOverlay";
import {Button} from "@mui/material";
import SearchField from "./components/SearchField";
import {AccountsTable, GoodsTable, OrdersTable} from ".";
import type {IAccount, IAdminContent, IAdminTableProps, IGood, IOrder} from "../../../types/AdminTypes";
import {
    createNewAccount,
    createNewGood,
    createNewOrder,
    deleteExistingAccount,
    deleteExistingGood,
    deleteExistingOrder,
    getAccount,
    getAccounts,
    getGood,
    getGoods,
    getOrder,
    getOrders,
    updateExistingAccount,
    updateExistingGood,
    updateExistingOrder
} from "src/utils/API";
import {ApplicationContext} from "../../applicationContext";

const AdminPage = <T extends IAdminContent>() => {

    const params = useParams();

    const context = useContext(ApplicationContext);

    const resolveTable = (): JSX.Element => {
        switch (params.table) {
            case "accounts" :
                const accountsProps: PropsWithChildren<IAdminTableProps<IAccount>> = {
                    getDataCallback: (c) => getAccounts(c),
                    createCallback: (c, e) => createNewAccount(c, e),
                    updateCallback: (c, e) => updateExistingAccount(c, e),
                    deleteCallback: (c, e) => deleteExistingAccount(c, e),
                    refreshCallback: (c, e) => getAccount(c, e.login),
                    columns: 6,
                };
                return <AccountsTable {...accountsProps} />;
            case "goods" :
                const goodsProps: PropsWithChildren<IAdminTableProps<IGood>> = {
                    getDataCallback: (c) => getGoods(c),
                    createCallback: (c, e) => createNewGood(c, e),
                    updateCallback: (c, e) => updateExistingGood(c, e),
                    deleteCallback: (c, e) => deleteExistingGood(c, e),
                    refreshCallback: (c, e) => getGood(c, e.id),
                    columns: 5,
                };
                return <GoodsTable {...goodsProps} />;
            case "orders" :
                const ordersProps: PropsWithChildren<IAdminTableProps<IOrder>> = {
                    getDataCallback: (c) => getOrders(c),
                    createCallback: (c, e) => createNewOrder(c, e),
                    updateCallback: (c, e) => updateExistingOrder(c, e),
                    deleteCallback: (c, e) => deleteExistingOrder(c, e),
                    refreshCallback: (c, e) => getOrder(c, e.id),
                    columns: 5,
                };

                return <OrdersTable {...ordersProps}  />;
            default:
                return <div/>;
        }
    }


    return context.authorized
        ? (
            <>
                <ButtonBar
                    items={[
                        {element: (<Button><Link to="/">Main</Link></Button>), adminButton: false},
                        {element: (<Button><Link to="/admin/accounts">Accounts</Link></Button>), adminButton: true},
                        {element: (<Button><Link to="/admin/goods">Goods</Link></Button>), adminButton: true},
                        {element: (<Button><Link to="/admin/orders">Orders</Link></Button>), adminButton: true}
                    ]}
                />
                <SearchField/>
                {resolveTable()}
            </>
        ) : (
            <AuthorizationOverlay/>
        );
}

export default AdminPage;
