import React, {useEffect, useState} from "react";
import {
    Box,
    Button,
    Dialog,
    DialogContent,
    DialogTitle,
    IconButton,
    MenuItem,
    Select,
    Table,
    TableBody,
    TableCell,
    TableHead,
    TableRow,
    TextField,
    Typography
} from "@mui/material";
import type {
    AdminAccountDto,
    AdminCountedGoodDto,
    AdminOrderDto,
    IAdminTableProps,
    IAdminTableState,
    Status
} from "../../../types";
import {
    checkFilterCondition,
    commonCreateBodyRow,
    commonCreateHeaderRow,
    commonCreatePlusRow,
    commonRenderActionsInput
} from "../../../utils/admin-tables";
import {
    getAccountsInAdminAreaByOrderIds,
    getAllOrdersInAdminArea,
    getGoodsInAdminAreaByOrderIds
} from "../../../utils/API";
import {InlineSpinner, SpinnerOverlay} from "../../../components/Spinner";
import {Close} from "@mui/icons-material";
import {useStore} from "effector-react";
import {IStaticsStore, staticsStore} from "../../../store/StaticsStore";
import {adminFilterStore} from "../../../store/AdminFilterStore";
import {ADMIN_ORDERS_KEY} from "../admin.page";

type IEntitiesFetching<T> = {
    id?: number;
    entities: T[];
}

const DetailsModal: React.FC<{
    closeCallback: () => void;
    open: boolean;
    header?: any;
    data?: any[];
    footer?: any;
}> = (props) => {
    return (
        <Dialog
            open={props.open}
        >
            {props.header && <DialogTitle
                sx={{
                    display: "flex"
                }}
            >
                <Typography variant="h4">{props.header}</Typography>
                <Box sx={{flexGrow: 1}}/>
                <IconButton onClick={props.closeCallback}>
                    <Close/>
                </IconButton>
            </DialogTitle>}
            {props.data && <DialogContent>
                {props.data}
                <hr/>
                {props.footer && <Typography variant="body2">{props.footer}</Typography>}
            </DialogContent>}
        </Dialog>
    );
};

const OrdersTable: React.FC<IAdminTableProps<AdminOrderDto>> = (props) => {

    const contextStore = useStore<IStaticsStore>(staticsStore);

    const adminFilter = useStore(adminFilterStore);

    const [state, setState] = useState<IAdminTableState<AdminOrderDto>>({
        isLoading: true,
        statuses: contextStore.statuses,
        accountRoles: contextStore.accountRoles,
        rows: [],
        sortBy: "",
    });

    const [fetchingState, setFetchingState] = useState<{
        loginFetching: boolean;
        logins: IEntitiesFetching<AdminAccountDto>[];
        goodsFetching: boolean;
        goods: IEntitiesFetching<AdminCountedGoodDto>[];
    }>({
        loginFetching: false,
        logins: [],
        goodsFetching: false,
        goods: [],
    });

    const [modalState, setModalState] = useState<{
        isModalOpened: boolean;
        modalHeader: any;
        modalData: any;
        modalFooter: any;
    }>({
        isModalOpened: false,
        modalHeader: "Detailed info",
        modalData: undefined,
        modalFooter: undefined
    });


    useEffect(() => {
        if (state.isLoading) {
            getAllOrdersInAdminArea()
                .then(r => {
                    setState(prevState => {
                        return {
                            ...prevState,
                            rows: r.data,
                            isLoading: false
                        };
                    });
                    setFetchingState((prev) => {
                        return {
                            ...prev,
                            loginFetching: true,
                            goodsFetching: true
                        };
                    });
                })
        }
    }, [state.isLoading]);

    useEffect(() => {
        if (fetchingState.loginFetching) {
            getAccountsInAdminAreaByOrderIds(
                state.rows.map((i) => i.id || -1).filter((i) => i !== -1)
            ).then((result) => {
                setFetchingState((prev) => {
                    return {
                        ...prev,
                        loginFetching: false,
                        logins: result.data.map((a) => {
                            return {
                                id: a.orderId,
                                entities: (a.accounts || []).map((l) => {
                                    return {login: l} as AdminAccountDto
                                })
                            };
                        })
                    };
                });
            })
        }
    }, [fetchingState.loginFetching, state.rows]);

    useEffect(() => {
        if (fetchingState.goodsFetching) {
            getGoodsInAdminAreaByOrderIds(
                state.rows.map((i) => i.id || -1).filter((i) => i !== -1)
            ).then((result) => {
                setFetchingState((prev) => {
                    return {
                        ...prev,
                        goodsFetching: false,
                        goods: result.data.map((g) => {
                            return {
                                id: g.orderId,
                                entities: g.goods || []
                            };
                        })
                    };
                })
            })
        }
    }, [fetchingState.goodsFetching, state.rows]);

    const switchModalState = (e: any, entity: any, footerText: string) => {
        setModalState(prevState => {
            return {
                ...prevState,
                isModalOpened: !prevState.isModalOpened,
                modalData: entity,
                modalFooter: footerText
            };
        });
    }

    const createHeaderRow = () => commonCreateHeaderRow(
        "header-AdminOrderDto",
        [
            {columnNumber: 0, width: "5%", align: "center", key: "id", value: "ID"},
            {columnNumber: 1, width: "40%", key: "info", value: "Info"},
            {columnNumber: 2, width: "15%", key: "status", value: "Status"},
            {columnNumber: 3, width: "10%", key: "login", value: "Login"},
            {columnNumber: 4, width: "10%", key: "goods", value: "Goods"},
            {columnNumber: 5, width: "20%", align: "center", key: "actions", value: "Actions"},
        ]);

    const renderActionsInput = (entity: AdminOrderDto) => commonRenderActionsInput<AdminOrderDto>(
        entity,
        {
            save: entity.info !== undefined && entity.info.trim() !== "",
            delete: entity.id !== undefined,
            refresh: true
        },
        props,
        setState
    );

    const createBodyRow = (entity: AdminOrderDto) => commonCreateBodyRow(
        `row-${state.rows.indexOf(entity)}`,
        [
            {columnNumber: 0, key: "id", content: entity.id},
            {columnNumber: 1, key: "info", content: renderInfoInput(entity)},
            {columnNumber: 2, key: "status", content: renderStatusInput(entity)},
            {columnNumber: 3, key: "login", content: renderCustomerCell(entity)},
            {columnNumber: 4, key: "goods", content: renderContentCell(entity)},
            {columnNumber: 5, key: "actions", align: "center", content: renderActionsInput(entity)},
        ]
    );

    const renderInfoInput = (entity: AdminOrderDto) => {
        return (
            <TextField
                fullWidth={true}
                label="Information"
                required
                value={entity.info}
                onChange={(e) => {
                    setState(prevState => {
                        const index = prevState.rows.indexOf(entity);
                        const newRows = [...prevState.rows];
                        newRows[index] = {...prevState.rows[index], info: e.target.value};
                        return {...prevState, rows: newRows};
                    });
                }}
            />
        );
    }

    const renderStatusInput = (entity: AdminOrderDto) => {
        return (
            <Select
                value={entity.status}
                onChange={(e) => {
                    setState({
                        ...state,
                        rows: [
                            ...state.rows.filter(r => r !== entity),
                            {...entity, status: (e.target.value) as Status}
                        ]
                    })
                }}
                defaultValue={""}
            >
                {
                    contextStore.statuses.map(item => (
                        <MenuItem
                            key={item}
                            value={item}
                        >
                            {item}
                        </MenuItem>
                    ))
                }
            </Select>
        );
    };

    const renderCustomerCell = (entity: AdminOrderDto) => {
        return fetchingState.loginFetching
            ? (<InlineSpinner/>)
            : (
                <Button
                    onClick={(e) => switchModalState(
                        e,
                        fetchingState.logins
                            .filter(g => g.id === entity.id)[0]
                            ?.entities
                            .map(g => (<Typography variant="body1">{g.login}</Typography>)),
                        ""
                    )}
                >
                    details
                </Button>
            );
    }

    const renderContentCell = (entity: AdminOrderDto) => {
        return fetchingState.goodsFetching
            ? (<InlineSpinner/>)
            : (
                <Button
                    onClick={(e) => switchModalState(
                        e,
                        (<Table>
                            <TableHead>
                                <TableRow>
                                    <TableCell key="count">Count</TableCell>
                                    <TableCell key="name">Name</TableCell>
                                    <TableCell key="article">Article</TableCell>
                                    <TableCell key="price">Price</TableCell>
                                </TableRow>
                            </TableHead>
                            <TableBody>
                                {fetchingState.goods
                                    .filter(g => g.id === entity.id)[0]
                                    ?.entities
                                    .map(g => (<TableRow>
                                        <TableCell
                                            key={`count-${g.id}`}
                                        >
                                            {g.count}
                                        </TableCell>
                                        <TableCell
                                            key={`name-${g.id}`}
                                        >
                                            {g.name}
                                        </TableCell>
                                        <TableCell
                                            key={`article-${g.id}`}
                                        >
                                            {g.article}
                                        </TableCell>
                                        <TableCell
                                            key={`price-${g.id}`}
                                        >
                                            {g.price}{' RUB'}
                                        </TableCell>
                                    </TableRow>))
                                }
                            </TableBody>

                        </Table>)


                        ,
                        "Total price: " + fetchingState.goods
                            .filter(g => g.id === entity.id)[0]
                            ?.entities
                            .map(g => (g?.count !== undefined ? g?.count : 0) * (g.price !== undefined ? g.price : 0))
                            .reduce((p, c) => p + c, 0) + " RUB"
                    )}
                >
                    details
                </Button>
            );
    }

    // useEffect(() => {
    //     setFetchingState(prevState => {
    //         return {
    //             ...prevState,
    //             loginFetching: state.rows.map((o, i): IFetching => {
    //                 return {id: o.id, isFetching: true};
    //             }),
    //             logins: state.rows.map((o, i): IEntitiesFetching<AdminAccountDto> => {
    //                 return {id: o.id, entities: []};
    //             }),
    //             goodsFetching: state.rows.map((o, i): IFetching => {
    //                 return {id: o.id, isFetching: true};
    //             }),
    //             goods: state.rows.map((o, i): IEntitiesFetching<AdminGoodDto & { count: number }> => {
    //                 return {id: o.id, entities: []};
    //             })
    //         };
    //     });
    //     state.rows.map((o, i) => {
    //         getAccountsInAdminAreaByOrderId(o.id)
    //             .then(response => {
    //                 setFetchingState(prevState => {
    //                     const indexLf = prevState.loginFetching.indexOf(prevState.loginFetching.filter(lf => lf.id === o.id)[0]);
    //                     const newLoginFetching = [...prevState.loginFetching];
    //                     newLoginFetching[indexLf] = {id: o.id, isFetching: false};
    //
    //                     const indexLs = prevState.logins.indexOf(prevState.logins.filter(ls => ls.id === o.id)[0]);
    //                     const newLogins = [...prevState.logins];
    //                     newLogins[indexLs] = {id: o.id, entities: response.data};
    //                     return {
    //                         ...prevState,
    //                         loginFetching: newLoginFetching,
    //                         logins: newLogins
    //                     };
    //                 });
    //             });
    //         getGoodsInAdminAreaByOrderId(o.id)
    //             .then(response => {
    //                 setFetchingState(prevState => {
    //                     const indexGf = prevState.goodsFetching.indexOf(prevState.goodsFetching.filter(gf => gf.id === o.id)[0]);
    //                     const newGoodsFetching = [...prevState.goodsFetching];
    //                     newGoodsFetching[indexGf] = {id: o.id, isFetching: false};
    //
    //                     const indexGs = prevState.goods.indexOf(prevState.goods.filter(gs => gs.id === o.id)[0]);
    //                     const newGoods = [...prevState.goods];
    //                     newGoods[indexGs] = {id: o.id, entities: [response.data]};
    //                     return {
    //                         ...prevState,
    //                         goodsFetching: newGoodsFetching,
    //                         goods: newGoods
    //                     };
    //                 });
    //             });
    //     });
    // }, [state.rows]);

    return (
        (state.isLoading === undefined || state.isLoading)
            ? (
                <SpinnerOverlay/>
            ) : (
                <>
                    <DetailsModal
                        closeCallback={() => setModalState(prevState => {
                            return {...prevState, isModalOpened: false};
                        })}
                        open={modalState.isModalOpened}
                        header={modalState.modalHeader}
                        data={modalState.modalData}
                        footer={modalState.modalFooter}
                    />
                    <Table>
                        <TableHead>
                            {createHeaderRow()}
                        </TableHead>
                        <TableBody>
                            {
                                state.rows
                                    .filter(r => checkFilterCondition(ADMIN_ORDERS_KEY, adminFilter, r.info))
                                    .sort((r1, r2) => (r1 && r1.id ? r1.id : 0xffff) - (r2 && r2.id ? r2.id : 0xffff))
                                    .map(r => createBodyRow(r as AdminOrderDto))
                            }
                            {
                                commonCreatePlusRow<AdminOrderDto>(
                                    props.columns,
                                    {
                                        id: undefined,
                                        info: "",
                                        status: "ADDED",
                                    },
                                    setState
                                )
                            }
                        </TableBody>
                    </Table>
                </>
            )
    );
}

export default OrdersTable;
