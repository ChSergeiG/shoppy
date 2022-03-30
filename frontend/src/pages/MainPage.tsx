import React, {useContext, useEffect} from "react";
import {Box, Paper} from "@mui/material";
import GoodCard from "../components/GoodCard";
import {Login, Logout, ManageAccounts, ShoppingBag} from "@mui/icons-material";
import {ApplicationContext} from "../applicationContext";
import type {IButtonBarItem} from "../components/ButtonBar";

const MainPage: React.FC = () => {

    const context = useContext(ApplicationContext);

    const buttonsForBar = (): IButtonBarItem[] => {
        const buttons: IButtonBarItem[] = [];
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
                    icon: <ShoppingBag/>
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
                    index: 40,
                    text: "Logout",
                    icon: <Logout/>
                }
            );
        } else {
            buttons.push({
                routerLinkProps: {to: "/admin/accounts"},
                adminButton: true,
                index: 10,
                text: "Login",
                icon: <Login/>
            });
        }
        return buttons;
    };

    useEffect(
        () => context.setButtonBarItems?.(buttonsForBar()),
        [context.authorized]
    );

    return (
        <Box>
            <Paper
                sx={{display: "flex"}}
            >
                <GoodCard
                    sx={{
                        m: "10px",
                        maxWidth: "200px"
                    }}
                    good={{
                        id: 1,
                        name: "well",
                        price: 17.4,
                        article: "asd-4445-ol",
                        status: "ACTIVE"
                    }}
                />
                <GoodCard
                    sx={{
                        m: "10px",
                        maxWidth: "200px"
                    }}
                    good={{
                        id: 2,
                        name: "well",
                        price: 17.4,
                        article: "asd-4445-ol",
                        status: "ACTIVE"
                    }}
                />
                <GoodCard
                    sx={{
                        m: "10px",
                        maxWidth: "200px"
                    }}
                    good={{
                        id: 3,
                        name: "well",
                        price: 17.4,
                        article: "asd-4445-ol",
                        status: "ACTIVE"
                    }}
                />
            </Paper>
        </Box>
    );
}

export default MainPage;