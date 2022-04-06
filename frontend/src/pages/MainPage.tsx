import React, {useContext, useEffect, useState} from "react";
import {Box, Paper} from "@mui/material";
import GoodCard from "../components/GoodCard";
import {Login, Logout, ManageAccounts, ShoppingBag, ShoppingBasket} from "@mui/icons-material";
import {ApplicationContext} from "../applicationContext";
import type {IButtonBarItem} from "../components/ButtonBar";
import type {IGood} from "../../types/AdminTypes";
import {getAllGoods} from "../utils/API";

const MainPage: React.FC<React.PropsWithChildren<{}>> = (props) => {

    const context = useContext(ApplicationContext);

    const [state, setState] = useState<{ goods: IGood[] }>({goods: []});

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

    useEffect(
        () => {
            getAllGoods(undefined, 0, 5)
                .then((r) => {
                    setState((prevState => {
                        return {...prevState, goods: r.data.content};
                    }))
                })
                .catch((_) => {
                    context.setSnackBarValues?.({message: "Cant get goods", color: "error"});
                });
        },
        []
    );

    return (
        <Box>
            <Paper
                sx={{display: "flex"}}
            >
                {
                    state.goods.map((g) =>
                        <GoodCard
                            sx={{
                                m: "10px",
                                width: "250px"
                            }}
                            variant="outlined"
                            good={g}
                            key={`good-${g.article}`}
                        />
                    )
                }
            </Paper>
        </Box>
    );
}

export default MainPage;