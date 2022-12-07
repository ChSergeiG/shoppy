import React, {useCallback, useEffect, useState} from "react";
import {Box, Grid} from "@mui/material";
import GoodCard from "../components/GoodCard";
import {Login, Logout, ManageAccounts, ShoppingBag, ShoppingBasket} from "@mui/icons-material";
import type {IButtonBarItem} from "../components/ButtonBar";
import {getAllGoodsUsingFilterAndPagination} from "../utils/API";
import {authorizationStore} from "../store/UserAuthorizationStore";
import {useStore} from "effector-react";
import {placeSnackBarAlert} from "../store/SnackBarStore";
import {setButtons} from "../store/ButtonBarStore";
import type * as T from "../types";

const MainPage: React.FC<React.PropsWithChildren<{}>> = (props) => {

    document.title = "Main page";

    const authStore = useStore(authorizationStore);

    const [state, setState] = useState<{ goods: T.CommonGoodDto[] }>({goods: []});

    const buttonsForBar = useCallback((): IButtonBarItem[] => {
        const buttons: IButtonBarItem[] = [];
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
    }, [authStore.authorized]);

    useEffect(
        () => {
            setButtons(buttonsForBar())
        },
        [authStore.authorized, buttonsForBar]
    );

    useEffect(
        () => {
            getAllGoodsUsingFilterAndPagination({
                params: {
                    filter: '',
                    page: 0,
                    size: 25,
                }
            })
                .then((r) => {
                    console.log(r)
                    setState((prevState => {
                        return {...prevState, goods: r.data};
                    }))
                })
                .catch((_) => {
                    placeSnackBarAlert({message: "Cant get goods", color: "error"});
                });
        },
        []
    );

    return (
        <Box>
            <Grid
                container
                spacing={2}
            >
                {
                    state.goods?.map((g) =>
                        <Grid
                            item
                            xs={3}
                        >
                            <GoodCard
                                sx={{
                                    m: "10px",
                                    width: "250px"
                                }}
                                variant="outlined"
                                good={g}
                                key={`good-${g.article}`}
                            />
                        </Grid>
                    )
                }
            </Grid>
        </Box>
    );
}

export default MainPage;