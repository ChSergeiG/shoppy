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
import type {IAccount, IAdminTableProps, IAdminTableState, IGood, IOrder} from "../../../../types/AdminTypes";
import {ApplicationContext} from "../../../applicationContext";
import {
    checkFilterCondition,
    commonCreateBodyRow,
    commonCreateHeaderRow,
    commonCreatePlusRow,
    commonRenderActionsInput
} from "../../../utils/admin-tables";
import {getAccountsByOrderId, getGoodsByOrderId, getOrders} from "../../../utils/API";
import type {IStatus} from "../../../../types/IStatus";
import {InlineSpinner, SpinnerOverlay} from "../../../components/Spinner";
import {Close} from "@mui/icons-material";
import {useStore} from "effector-react";
import {IStaticsStore, staticsStore} from "../../../store/StaticsStore";

type IFetching = {
    id?: number;
    isFetching: boolean;
};

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

const OrdersTable: React.FC<IAdminTableProps<IOrder>> = (props) => {

    const context = React.useContext(ApplicationContext);

    const contextStore = useStore<IStaticsStore>(staticsStore);

    const [state, setState] = useState<IAdminTableState<IOrder>>({
        isLoading: true,
        statuses: contextStore.statuses,
        accountRoles: contextStore.accountRoles,
        rows: [],
        sortBy: "",
    });

    const [fetchingState, setFetchingState] = useState<{
        loginFetching: IFetching[];
        logins: IEntitiesFetching<IAccount>[];
        goodsFetching: IFetching[];
        goods: IEntitiesFetching<IGood & { count: number }>[];
    }>({
        loginFetching: [],
        logins: [],
        goodsFetching: [],
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
        "header-IOrder",
        [
            {columnNumber: 0, width: "5%", align: "center", key: "id", value: "ID"},
            {columnNumber: 1, width: "40%", key: "info", value: "Info"},
            {columnNumber: 2, width: "15%", key: "status", value: "Status"},
            {columnNumber: 3, width: "10%", key: "login", value: "Login"},
            {columnNumber: 4, width: "10%", key: "goods", value: "Goods"},
            {columnNumber: 5, width: "20%", align: "center", key: "actions", value: "Actions"},
        ]);


    const renderActionsInput = (entity: IOrder) => commonRenderActionsInput<IOrder>(
        entity,
        {
            save: entity.info !== undefined && entity.info.trim() !== "",
            delete: entity.id !== undefined,
            refresh: true
        },
        props,
        setState
    );


    const createBodyRow = (entity: IOrder) => commonCreateBodyRow(
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

    const renderInfoInput = (entity: IOrder) => {
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

    const renderStatusInput = (entity: IOrder) => {
        return (
            <Select
                value={entity.status}
                onChange={(e) => {
                    setState({
                        ...state,
                        rows: [
                            ...state.rows.filter(r => r !== entity),
                            {...entity, status: (e.target.value) as IStatus}
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

    const renderCustomerCell = (entity: IOrder) => {
        return fetchingState.loginFetching.filter(lf => lf.id === entity.id)?.[0]?.isFetching
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

    const renderContentCell = (entity: IOrder) => {
        return fetchingState.goodsFetching.filter(lf => lf.id === entity.id)?.[0]?.isFetching
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
                            .map(g => g.count * g.price)
                            .reduce((p, c) => p + c, 0) + " RUB"
                    )}
                >
                    details
                </Button>
            );
    }

    useEffect(() => {
        getOrders()
            .then(r => {
                setState(prevState => {
                    return {...prevState, rows: r.data, isLoading: false};
                });
            })
    }, []);

    useEffect(() => {
        setFetchingState(prevState => {
            return {
                ...prevState,
                loginFetching: state.rows.map((o, i): IFetching => {
                    return {id: o.id, isFetching: true};
                }),
                logins: state.rows.map((o, i): IEntitiesFetching<IAccount> => {
                    return {id: o.id, entities: []};
                }),
                goodsFetching: state.rows.map((o, i): IFetching => {
                    return {id: o.id, isFetching: true};
                }),
                goods: state.rows.map((o, i): IEntitiesFetching<IGood & { count: number }> => {
                    return {id: o.id, entities: []};
                })
            };
        });
        state.rows.map((o, i) => {
            getAccountsByOrderId(o)
                .then(response => {
                    setFetchingState(prevState => {
                        const indexLf = prevState.loginFetching.indexOf(prevState.loginFetching.filter(lf => lf.id === o.id)[0]);
                        const newLoginFetching = [...prevState.loginFetching];
                        newLoginFetching[indexLf] = {id: o.id, isFetching: false};

                        const indexLs = prevState.logins.indexOf(prevState.logins.filter(ls => ls.id === o.id)[0]);
                        const newLogins = [...prevState.logins];
                        newLogins[indexLs] = {id: o.id, entities: response.data};
                        return {
                            ...prevState,
                            loginFetching: newLoginFetching,
                            logins: newLogins
                        };
                    });
                });
            getGoodsByOrderId(o)
                .then(response => {
                    setFetchingState(prevState => {
                        const indexGf = prevState.goodsFetching.indexOf(prevState.goodsFetching.filter(gf => gf.id === o.id)[0]);
                        const newGoodsFetching = [...prevState.goodsFetching];
                        newGoodsFetching[indexGf] = {id: o.id, isFetching: false};

                        const indexGs = prevState.goods.indexOf(prevState.goods.filter(gs => gs.id === o.id)[0]);
                        const newGoods = [...prevState.goods];
                        newGoods[indexGs] = {id: o.id, entities: response.data};
                        return {
                            ...prevState,
                            goodsFetching: newGoodsFetching,
                            goods: newGoods
                        };
                    });
                });
        });
    }, [state.rows]);

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
                                    .filter(r => checkFilterCondition(context.adminFilter, r.info))
                                    .sort((r1, r2) => (r1 && r1.id ? r1.id : 0xffff) - (r2 && r2.id ? r2.id : 0xffff))
                                    .map(r => createBodyRow(r as IOrder))
                            }
                            {
                                commonCreatePlusRow<IOrder>(
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
