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
import {authorizationStore, IAuthorizationStore} from "../../store/UserAuthorizationStore";
import {useStore} from "effector-react";

const AdminPage = <T extends IAdminContent>() => {

    const params = useParams();

    const context = useContext(ApplicationContext);
    const authStore = useStore<IAuthorizationStore>(authorizationStore);

    const resolveTable = (): JSX.Element => {
        switch (params.table) {
            case "accounts" :
                document.title = "Edit account page";
                const accountsProps: PropsWithChildren<IAdminTableProps<IAccount>> = {
                    getDataCallback: () => getAccounts(),
                    createCallback: (e) => createNewAccount(e),
                    updateCallback: (e) => updateExistingAccount(e),
                    deleteCallback: (e) => deleteExistingAccount(e),
                    refreshCallback: (e) => getAccount(e.login),
                    columns: 6,
                };
                return <AccountsTable {...accountsProps} />;
            case "goods" :
                document.title = "Edit goods page";
                const goodsProps: PropsWithChildren<IAdminTableProps<IGood>> = {
                    getDataCallback: () => getGoods(),
                    createCallback: (e) => createNewGood(e),
                    updateCallback: (e) => updateExistingGood(e),
                    deleteCallback: (e) => deleteExistingGood(e),
                    refreshCallback: (e) => getGood(e.id),
                    columns: 6,
                };
                return <GoodsTable {...goodsProps} />;
            case "orders" :
                document.title = "Edit orders page";
                const ordersProps: PropsWithChildren<IAdminTableProps<IOrder>> = {
                    getDataCallback: () => getOrders(),
                    createCallback: (e) => createNewOrder(e),
                    updateCallback: (e) => updateExistingOrder(e),
                    deleteCallback: (e) => deleteExistingOrder(e),
                    refreshCallback: (e) => getOrder(e.id),
                    columns: 6,
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
        if (authStore.authorized) {
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
        [authStore.authorized]
    );

    return authStore.authorized
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
