import React, {PropsWithChildren, useContext, useEffect} from "react";
import {useParams} from "react-router-dom";
import AuthorizationOverlay from "./components/AuthorizationOverlay";
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
import {Home, Logout, ManageAccounts, ShoppingBag, ShoppingBasket} from "@mui/icons-material";
import type {IButtonBarItem} from "../../components/ButtonBar";
import {Box} from "@mui/material";

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

    const buttonsForBar = (): IButtonBarItem[] => {
        const buttons = [
            {
                routerLinkProps: {to: "/"},
                adminButton: false,
                index: -1,
                text: "Home",
                icon: <Home/>
            }
        ]
        if (context.authorized) {
            buttons.push(
                {
                    routerLinkProps: {to: "/admin/accounts"},
                    adminButton: true,
                    index: 10,
                    text: "Accounts",
                    icon: <ManageAccounts/>
                },
                {
                    routerLinkProps: {to: "/admin/goods"},
                    adminButton: true,
                    index: 20,
                    text: "Goods",
                    icon: <ShoppingBasket/>
                },
                {
                    routerLinkProps: {to: "/admin/orders"},
                    adminButton: true,
                    index: 30,
                    text: "Orders",
                    icon: <ShoppingBag/>
                },
                {
                    routerLinkProps: {to: "/"},
                    adminButton: true,
                    index: 1000,
                    text: "Logout",
                    icon: <Logout/>
                }
            );
        }
        return buttons;
    }

    useEffect(
        () => context.setButtonBarItems?.(buttonsForBar()),
        [context.authorized]
    );

    return context.authorized
        ? (
            <Box>
                <SearchField/>
                {resolveTable()}
            </Box>
        ) : (
            <AuthorizationOverlay/>
        );
}

export default AdminPage;
