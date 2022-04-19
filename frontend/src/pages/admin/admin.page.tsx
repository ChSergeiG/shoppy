import React, {PropsWithChildren, useEffect} from "react";
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
import {Home, Logout, ManageAccounts, ShoppingBag, ShoppingBasket} from "@mui/icons-material";
import type {IButtonBarItem} from "../../components/ButtonBar";
import {Box} from "@mui/material";
import {authorizationStore, IAuthorizationStore} from "../../store/UserAuthorizationStore";
import {useStore} from "effector-react";
import {setButtons} from "../../store/ButtonBarStore";

export const ADMIN_ACCOUNTS_KEY = "accounts";
export const ADMIN_GOODS_KEY = "goods";
export const ADMIN_ORDERS_KEY = "orders";

const AdminPage = <T extends IAdminContent>() => {

    const params = useParams();

    const authStore = useStore<IAuthorizationStore>(authorizationStore);

    const searchField = (): JSX.Element => {
        switch (params.table) {
            case ADMIN_ACCOUNTS_KEY :
            case ADMIN_GOODS_KEY :
            case ADMIN_ORDERS_KEY:
                return <SearchField
                    searchKey={params.table}
                />
            default:
                return <div/>;
        }
    };

    const resolveTable = (): JSX.Element => {
        switch (params.table) {
            case ADMIN_ACCOUNTS_KEY :
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
            case ADMIN_GOODS_KEY :
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
            case ADMIN_ORDERS_KEY:
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
        () => {
            setButtons(buttonsForBar())
        },
        [authStore.authorized]
    );

    return authStore.authorized
        ? (
            <Box>
                {searchField()}
                {resolveTable()}
            </Box>
        ) : (
            <AuthorizationOverlay/>
        );
}

export default AdminPage;
